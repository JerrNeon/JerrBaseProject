package com.cw.jerrbase.base.api;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (界面跳转, 适用于Activity和Fragment)
 * @create by: chenwei
 * @date 2017/3/14 13:43
 */
public interface IRoute3 extends IRoute2 {

    /**
     * 通过类名启动Activity，并且含有Bundle数据，并会再打开另一个activity
     * 例如：登录成功后需要打开新的activity（@param targetcls）
     *
     * @param cls               需要跳转的类
     * @param bundle            数据
     * @param targetPackageName 要跳转的类的包名
     */
    void openActivity(@NonNull Class<?> cls, @NonNull String targetPackageName, @Nullable Bundle bundle);

    /**
     * 只有调用了openActivity(Class<?> cls, String targetPackageName, Bundle bundle)此方法才才有效
     *
     * @param bundle 数据
     */
    void openTargetActivity(@Nullable Bundle bundle, @NonNull String targetPackageName);
}
