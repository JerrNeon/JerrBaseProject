package com.cw.jerrbase.net.request;

import android.util.Pair;
import android.widget.ImageView;

import com.cw.jerrbase.net.OkHttpClientManager;
import com.cw.jerrbase.net.cache.InterceptorCache;
import com.cw.jerrbase.net.callback.ResultCallback;
import com.cw.jerrbase.util.LogUtils;
import com.cw.jerrbase.util.QMUtil;
import com.google.gson.Gson;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by zhy on 15/11/6.
 */
public abstract class OkHttpRequest {
    protected OkHttpClientManager mOkHttpClientManager = OkHttpClientManager
            .getInstance();
    protected OkHttpClient mOkHttpClient;

    protected RequestBody requestBody;
    protected Request request;

    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected OkHttpRequest(String url, Object tag, Map<String, String> params,
                            Map<String, String> headers) {
        mOkHttpClient = mOkHttpClientManager.getOkHttpClient();
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
    }

    protected abstract Request buildRequest();

    protected abstract RequestBody buildRequestBody();

    protected void prepareInvoked(ResultCallback callback) {
        requestBody = buildRequestBody();
        requestBody = wrapRequestBody(requestBody, callback);
        request = buildRequest();
        // TODO: 2016/10/11  chenwei修改（设置缓存）
        mOkHttpClient.interceptors().add(InterceptorCache.REWRITE_CACHE_CONTROL_INTERCEPTOR);
        mOkHttpClient.networkInterceptors().add(InterceptorCache.REWRITE_CACHE_CONTROL_INTERCEPTOR);
        mOkHttpClient.setCache(InterceptorCache.CACHE);
    }

    public void invokeAsyn(ResultCallback callback) {
        prepareInvoked(callback);
        mOkHttpClientManager.execute(request, callback);
    }

    protected RequestBody wrapRequestBody(RequestBody requestBody,
                                          final ResultCallback callback) {
        return requestBody;
    }

    public <T> T invoke(Class<T> clazz) throws IOException {
        requestBody = buildRequestBody();
        Request request = buildRequest();
        return mOkHttpClientManager.execute(request, clazz);
    }

    protected void appendHeaders(Request.Builder builder,
                                 Map<String, String> headers) {
        if (builder == null) {
            throw new IllegalArgumentException("builder can not be empty!");
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty())
            return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    public void cancel() {
        if (tag != null)
            mOkHttpClientManager.cancelTag(tag);
    }

    public static class Builder {
        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Map<String, Object> params2;

        private Pair<String, File>[] files;
        private MediaType mediaType;

        private String destFileDir;
        private String destFileName;

        private ImageView imageView;
        private int errorResId = -1;

        // for post
        private String content = "";
        private byte[] bytes;
        private File file;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder params2(Map<String, Object> params2) {
            this.params2 = params2;
            return this;
        }

        public Builder addParams(String key, String val) {
            if (this.params == null) {
                params = new IdentityHashMap<String, String>();
            }
            params.put(key, val);
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder addHeader(String key, String val) {
            if (this.headers == null) {
                headers = new IdentityHashMap<String, String>();
            }
            headers.put(key, val);
            return this;
        }

        public Builder files(Pair<String, File>... files) {
            this.files = files;
            return this;
        }

        public Builder destFileName(String destFileName) {
            this.destFileName = destFileName;
            return this;
        }

        public Builder destFileDir(String destFileDir) {
            this.destFileDir = destFileDir;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder errResId(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder mediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public <T> T get(Class<T> clazz) throws IOException {
            OkHttpRequest request = new OkHttpGetRequest(url, tag, params,
                    headers);
            return request.invoke(clazz);
        }

        /**
         * 参数以表单形式传到服务器(只能设置params)
         *
         * @param callback
         * @return
         */
        public OkHttpRequest get(ResultCallback callback) {
            //这里传入tag 用于返回键取消请求
            tag(callback.getTag());
            OkHttpRequest request = new OkHttpGetRequest(url, tag, params,
                    headers);
            request.invokeAsyn(callback);
            return request;
        }

        public <T> T post(Class<T> clazz) throws IOException {
            OkHttpRequest request = new OkHttpPostRequest(url, tag, params,
                    headers, mediaType, content, bytes, file);
            return request.invoke(clazz);
        }

        /**
         * 参数是以JSON格式传到服务器的(设置params和params2都可以)
         *
         * @param callback
         * @return
         */
        public OkHttpRequest post(ResultCallback callback) {
            // 这里传入tag 用于返回键取消请求
            tag(callback.getTag());
            //todo dz修改
            if (QMUtil.isNotEmpty(params)) {
                //吧content修改掉成json串
                Gson gson = new Gson();
                content = gson.toJson(params);
                LogUtils.d("OkHttpPostRequest json--->" + content);
            }
            if (QMUtil.isNotEmpty(params2)) {
                //吧content修改掉成json串
                Gson gson = new Gson();
                content = gson.toJson(params2);
                LogUtils.d("OkHttpPostRequest json--->" + content);
            }
            OkHttpRequest request = new OkHttpPostRequest(url, tag, params,
                    headers, mediaType, content, bytes, file);
            request.invokeAsyn(callback);
            return request;
        }

        /**
         * 参数以表单形式传到服务器(只能设置params)
         *
         * @param callback
         * @return
         */
        // TODO: 2016/10/11 chenwei添加
        public OkHttpRequest postValiForm(ResultCallback callback) {
            // 这里传入tag 用于返回键取消请求
            tag(callback.getTag());
            OkHttpRequest request = new OkHttpPostRequest(url, tag, params,
                    headers, mediaType, null, bytes, file);
			LogUtils.d("OkHttpPostValiForm params--->" + params.toString());
            request.invokeAsyn(callback);
            return request;
        }

        public OkHttpRequest upload(ResultCallback callback) {
            //这里传入tag 用于返回键取消请求
            tag(callback.getTag());
            OkHttpRequest request = new OkHttpUploadRequest(url, tag, params,
                    headers, files);
            request.invokeAsyn(callback);
            return request;
        }

        public <T> T upload(Class<T> clazz) throws IOException {
            OkHttpRequest request = new OkHttpUploadRequest(url, tag, params,
                    headers, files);
            return request.invoke(clazz);
        }

        public OkHttpRequest download(ResultCallback callback) {
            // 这里传入tag 用于返回键取消请求
            tag(callback.getTag());
            OkHttpRequest request = new OkHttpDownloadRequest(url, tag, params,
                    headers, destFileName, destFileDir);
            request.invokeAsyn(callback);
            return request;
        }

        public String download() throws IOException {
            OkHttpRequest request = new OkHttpDownloadRequest(url, tag, params,
                    headers, destFileName, destFileDir);
            return request.invoke(String.class);
        }

        public void displayImage(ResultCallback callback) {
            //这里传入tag 用于返回键取消请求
            tag(callback.getTag());
            OkHttpRequest request = new OkHttpDisplayImgRequest(url, tag,
                    params, headers, imageView, errorResId);
            request.invokeAsyn(callback);
        }

    }

}
