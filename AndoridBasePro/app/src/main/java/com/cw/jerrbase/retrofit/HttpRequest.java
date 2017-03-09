package com.cw.jerrbase.retrofit;

import com.cw.jerrbase.BaseApplication;
import com.cw.jerrbase.common.ServerURL;
import com.cw.jerrbase.util.ImageUtil;
import com.cw.jerrbase.util.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (retrofit配置信息)
 * @create by: chenwei
 * @date 2016/10/9 15:23
 */
public class HttpRequest {

    /**
     * 默认超时时间 5s
     */
    private static final int DEFAULT_TIMEOUT = 5;
    /**
     * 缓存大小(10M)
     */
    private static final int cacheSize = 10 * 1024 * 1024;
    /**
     * 缓存路径
     */
    private static final File cacheFile = ImageUtil.getFileCacheFile();
    /**
     * Retrofit操作对象
     */
    private Retrofit mRetrofit;

    private HttpRequest() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(new Cache(cacheFile, cacheSize))
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ServerURL.baseUrl)
                .build();
    }

    /**
     * 在访问HttpRequest时创建单例
     */
    private static class SingletonHolder {
        public static final HttpRequest INSTANCE = new HttpRequest();
    }

    /**
     * 获取单例
     *
     * @return HttpRequest对象
     */
    public static HttpRequest getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    /**
     * get请求缓存拦截器(post请求无效)
     */
    public final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //通过 CacheControl 控制缓存数据
            //如果.maxAge(0,TimeUnit.SECONDS)设置的时间比拦截器长是不起效果，如果设置比拦截器设置的时间短就会以这个时间为主
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
            cacheBuilder.maxStale(365, TimeUnit.DAYS);//这个是控制缓存的过时时间
            CacheControl cacheControl = cacheBuilder.build();

            //设置拦截器
            Request request = chain.request();
            if (!NetUtils.isConnected(BaseApplication.getInstance().getApplicationContext())) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtils.isConnected(BaseApplication.getInstance().getApplicationContext())) {
                int maxAge = 0; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

}
