package com.cw.jerrbase.util;

import android.util.Log;

import com.cw.jerrbase.BaseApplication;

/**
 * Log统一管理类
 *
 * @author way
 */
public class LogUtil {

    private static final boolean isDebug = BaseApplication.LOG_DEBUG;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = BaseApplication.LOG_TAG;

    private LogUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void w(String msg) {
        if (isDebug)
            Log.w(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    /**
     * system 输出
     *
     * @param s
     */
    public static void sysOut(String s) {
        if (isDebug) {
            System.out.println(s);
        }
    }

    /**
     * system 输出
     *
     * @param s
     * @param TAG
     */
    public static void sysOut(String TAG, Object s) {
        if (isDebug) {
            System.out.println(TAG + ":>>> " + s);
        }
    }
}