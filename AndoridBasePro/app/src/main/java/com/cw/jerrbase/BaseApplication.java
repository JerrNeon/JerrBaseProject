package com.cw.jerrbase;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.cw.jerrbase.base.exception.CrashHandler;
import com.facebook.stetho.Stetho;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.squareup.leakcanary.LeakCanary;
import com.zhy.autolayout.config.AutoLayoutConifg;

import cn.jpush.android.api.JPushInterface;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/8/23 15:36
 */
public class BaseApplication extends Application {

    private static BaseApplication instance = null;
    public static final boolean LOG_DEBUG = BuildConfig.LOG_DEBUG;
    public static final String LOG_TAG = BuildConfig.LOG_TAG;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //initCrashHandler();
        initAutoLayout();
        initLeakCanary();
        initOkGo();
        initStetho();
        initBaiduMap();
        initJpush();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);//突破65536个方法数
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    private void initCrashHandler() {
        CrashHandler.getInstance().init(this);
    }

    private void initAutoLayout() {
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);
    }

    /**
     * 初始化内存泄漏分析
     */
    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this))
            return;
        LeakCanary.install(this);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }

    private void initBaiduMap() {
        SDKInitializer.initialize(this);
    }

    private void initJpush() {
        JPushInterface.setDebugMode(BuildConfig.LOG_DEBUG);
        JPushInterface.init(this);
    }

    /**
     * 初始化okGo
     */
    private void initOkGo() {
        //必须调用初始化
        OkGo.init(this);
        //以下设置的所有参数是全局参数,同样的参数可以在请求的时候再设置一遍,那么对于该请求来讲,请求中的参数会覆盖全局参数
        //好处是全局参数统一,特定请求可以特别定制参数
        try {
            //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
            OkGo.getInstance()
                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                    .debug("OkGo")
                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传
                    .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    //可以全局统一设置缓存时间,默认永不过期
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE);
            //如果不想让框架管理cookie,以下不需要
            //.setCookieStore(new MemoryCookieStore())     //cookie使用内存缓存（app退出后，cookie消失）
            //.setCookieStore(new PersistentCookieStore()) //cookie持久化存储，如果cookie不过期，则一直有效
            //可以设置https的证书,以下几种方案根据需要自己设置,不需要不用设置
            //.setCertificates()                                  //方法一：信任所有证书
            //.setCertificates(getAssets().open("srca.cer"))      //方法二：也可以自己设置https证书
            // .setCertificates(getAssets().open("aaaa.bks"), "123456", getAssets().open("srca.cer"))//方法三：传入bks证书,密码,和cer证书,支持双向加密
            //可以添加全局拦截器,不会用的千万不要传,错误写法直接导致任何回调不执行
            // .addInterceptor(new Interceptor() {
            //                    @Override
            //                    public Response intercept(Chain chain) throws IOException {
            //                        return chain.proceed(chain.request());
            //                    }
            //                })
            //这两行同上,不需要就不要传
            //.addCommonHeaders(headers)  //设置全局公共头
            //.addCommonParams(params);  //设置全局公共参数
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
