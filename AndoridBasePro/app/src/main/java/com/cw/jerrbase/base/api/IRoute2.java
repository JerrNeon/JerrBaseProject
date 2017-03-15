package com.cw.jerrbase.base.api;

import android.content.Intent;
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
public interface IRoute2 extends IRoute1 {

    /**
     * 通过类名启动Activity，并且含有Bundle数据和返回数据
     *
     * @param cls    需要跳转的类
     * @param bundle 数据
     */
    void openActivity(@NonNull Class<?> cls, @Nullable Bundle bundle, @NonNull int requestCode);

    /**
     * 数据回调
     *
     * @param requestCode 请求码
     * @param data        数据
     */
    void onActivityResult(@NonNull int requestCode, @NonNull Intent data);

}
