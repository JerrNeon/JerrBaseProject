package com.cw.jerrbase.base.api;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (界面跳转, 适用于Activity.Fragment和Adapter)
 * @create by: chenwei
 * @date 2017/3/14 13:37
 */
public interface IRoute extends IBase {

    int requestCode1 = 0x01;
    int requestCode2 = 0x02;
    int requestCode3 = 0x03;
    int requestCode4 = 0x04;

    int RESULT_OK_FLAG = Activity.RESULT_OK;//-1
    int RESULT_CANCELED_FLAG = Activity.RESULT_CANCELED;//0
    int RESULT_FIRST_USER_FLAG = Activity.RESULT_FIRST_USER;//1

    enum RouteType {
        ADD, DELETE, UPDATE, QUERY
    }

    String LAYOUT_LINEARLAYOUT = "LinearLayout";
    String LAYOUT_FRAMELAYOUT = "FrameLayout";
    String LAYOUT_RELATIVELAYOUT = "RelativeLayout";

    /**
     * 通过类名启动Activity
     *
     * @param cls 需要跳转的类
     */
    void openActivity(@NonNull Class<?> cls);

    /**
     * 通过类名启动Activity，并且含有Flag标识
     *
     * @param cls  需要跳转的类
     * @param flag 数据
     */
    void openActivity(@NonNull Class<?> cls, @NonNull int flag);

    /**
     * 通过类名启动Activity，并且只有一个long类型的数据
     *
     * @param cls   需要跳转的类
     * @param param 数据
     */
    void openActivity(@NonNull Class<?> cls, @NonNull long param);

    /**
     * 通过类名启动Activity，并且只有一个String类型的数据
     *
     * @param cls   需要跳转的类
     * @param param 数据
     */
    void openActivity(@NonNull Class<?> cls, @NonNull String param);

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param cls    需要跳转的类
     * @param bundle 数据
     */
    void openActivity(@NonNull Class<?> cls, @NonNull Bundle bundle);

}
