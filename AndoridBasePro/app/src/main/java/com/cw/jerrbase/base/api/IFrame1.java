package com.cw.jerrbase.base.api;

import android.support.v7.widget.Toolbar;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (基础框架)
 * @create by: chenwei
 * @date 2017/3/14 14:57
 */
public interface IFrame1 extends IFrame {

    /**
     * 设置ToolBar
     *
     * @param toolbar
     */
    void setToolBar(Toolbar toolbar);

    /**
     * 退出
     */
    void exit();
}
