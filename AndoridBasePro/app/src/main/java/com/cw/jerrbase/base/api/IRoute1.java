package com.cw.jerrbase.base.api;

import android.os.Bundle;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (界面跳转, 适用于Activity和Fragment)
 * @create by: chenwei
 * @date 2017/3/15 10:37
 */
public interface IRoute1 extends IRoute {

    int getInt();

    String getString();

    long getLong();

    Bundle getBundle();
}
