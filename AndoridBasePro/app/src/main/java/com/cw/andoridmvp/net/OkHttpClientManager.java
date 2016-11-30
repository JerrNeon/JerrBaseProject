package com.cw.andoridmvp.net;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.cw.andoridmvp.net.callback.ResultCallback;
import com.cw.andoridmvp.util.NLogUtil;
import com.cw.andoridmvp.util.date.CustomDateUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.ConnectException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpClientManager {

    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        // cookie enabled
        mOkHttpClient.setCookieHandler(new CookieManager(null,
                CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mDelivery = new Handler(Looper.getMainLooper());

        final int sdk = Build.VERSION.SDK_INT;
        if (sdk >= 23) {
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.FINAL,
                            Modifier.TRANSIENT, Modifier.STATIC);
            mGson = gsonBuilder.create();
        } else {
            mGson = new Gson();
        }

        // just for test
        if (false) {
            mOkHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }

    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public void execute(final Request request, ResultCallback callback) {
        if (callback == null)
            callback = ResultCallback.DEFAULT_RESULT_CALLBACK;
        final ResultCallback resCallBack = callback;
        resCallBack.onBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                // 打印请求地址
                NLogUtil.sysOut("Request URl", request.urlString());
                sendFailResultCallback(null, request, e, resCallBack);
            }

            @Override
            public void onResponse(final Response response) {
                /*
                 * if (response.code() >= 400 && response.code() <= 599) { try {
				 * sendFailResultCallback(request, new
				 * RuntimeException(response.body().string()), resCallBack); }
				 * catch (IOException e) { e.printStackTrace(); } return; }
				 */
                // 打印请求地址
                NLogUtil.sysOut("Request URl", request.urlString());

                NLogUtil.sysOut("response code", response.code() + "");
                // 404也会进来
                if (response.code() != 200) {
                    // 错误信息
                    sendFailResultCallback(null, response.request(),
                            new ConnectException(), resCallBack);
                    return;
                }
                try {
                    final String string = response.body().string();
                    NLogUtil.sysOut("response", string);
                    // 先解析成统一对象
                    XaResult result = mGson.fromJson(string, XaResult.class);

                    // 这里可以判断服务器返回的code 是否正确
                    if (!("0000").equals(result.getRspCode())) {
                        NLogUtil.sysOut("response", result.getRspCode());
                        // 错误信息
                        sendFailResultCallback(result, response.request(),
                                new OkHttpException(), resCallBack);
                        return;
                    }

                    if (resCallBack.mType == String.class) {
                        sendSuccessResultCallback(result, mGson.toJson(result.getData()),
                                resCallBack);
                    } else {
                        Object o = mGson.fromJson(mGson.toJson(result.getData()), resCallBack.mType);
                        // 此处加了判断 防止解析空 崩溃
                        // if (o == null) {
                        // sendFailedStringCallback(null, response.request(),
                        // new JsonPaserNullException(), resCallBack);
                        // } else {
                        sendSuccessResultCallback(result, o, resCallBack);
                        // }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    sendFailResultCallback(null, response.request(), e,
                            resCallBack);
                } catch (com.google.gson.JsonParseException e)// Json解析的错误
                {
                    e.printStackTrace();
                    sendFailResultCallback(null, response.request(), e,
                            resCallBack);
                }

            }
        });
    }

    public <T> T execute(Request request, Class<T> clazz) throws IOException {
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        String respStr = execute.body().string();
        return mGson.fromJson(respStr, clazz);
    }

    public <T> void sendFailResultCallback(final XaResult<T> result,
                                           final Request request, final Exception e,
                                           final ResultCallback callback) {
        if (callback == null)
            return;
        NLogUtil.logE("onFailure 异常", e.toString());
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(result, request, e);
                callback.onAfter();
            }
        });
    }

    public <T> void sendSuccessResultCallback(final XaResult<T> result, final Object object,
                                              final ResultCallback callback) {
        if (callback == null)
            return;
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object);
                callback.onAfter();
                //服务器数据同步时间
                if (result != null) {
                    long lastTime = -1;
                    if (!TextUtils.isEmpty(result.getCreateTime())) {
                        lastTime = CustomDateUtil.getLongTime(result.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                    }
                    callback.onUpDataTime(lastTime);
                }
            }
        });
    }

    public void cancelTag(Object tag) {
        mOkHttpClient.cancel(tag);
    }

    public void setCertificates(InputStream... certificates) {
        setCertificates(certificates, null, null);
    }

    private TrustManager[] prepareTrustManager(InputStream... certificates) {
        if (certificates == null || certificates.length <= 0)
            return null;
        try {

            CertificateFactory certificateFactory = CertificateFactory
                    .getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias,
                        certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e)

                {
                }
            }
            TrustManagerFactory trustManagerFactory = null;

            trustManagerFactory = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            TrustManager[] trustManagers = trustManagerFactory
                    .getTrustManagers();

            return trustManagers;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
        try {
            if (bksFile == null || password == null)
                return null;

            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory
                    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCertificates(InputStream[] certificates,
                                InputStream bksFile, String password) {
        try {
            TrustManager[] trustManagers = prepareTrustManager(certificates);
            KeyManager[] keyManagers = prepareKeyManager(bksFile, password);
            SSLContext sslContext = SSLContext.getInstance("TLS");

            sslContext.init(keyManagers,
                    new TrustManager[]{new MyTrustManager(
                            chooseTrustManager(trustManagers))},
                    new SecureRandom());
            mOkHttpClient.setSslSocketFactory(sslContext.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    private X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    private class MyTrustManager implements X509TrustManager {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public MyTrustManager(X509TrustManager localTrustManager)
                throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory var4 = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            var4.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

}