package com.cw.andoridmvp.net.cache;

import com.cw.andoridmvp.BaseApplication;
import com.cw.andoridmvp.util.ImageUtil;
import com.cw.andoridmvp.util.NetUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (缓存配置)
 * @create by: chenwei
 * @date 2016/10/11 16:57
 */
public class InterceptorCache {

    /**
     * 缓存大小(10M)
     */
    private static final int cacheSize = 10 * 1024 * 1024;
    /**
     * 缓存路径
     */
    private static final File cacheFile = ImageUtil.getFileCacheFile();

    public static final Cache CACHE =  new Cache(cacheFile, cacheSize);

    /**
     * get请求缓存拦截器(post请求无效)
     */
    public static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
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
