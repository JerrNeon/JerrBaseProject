package com.cw.jerrbase.base.api;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (基础框架)
 * @create by: chenwei
 * @date 2017/3/14 14:55
 */
public interface IFrame {

    /**
     * 初始化ButterKnife
     */
    void initButterKnife();

    /**
     * 初始化EventBus
     */
    void initEventBus();

    /**
     * 设置状态栏
     */
    void setStatusBar();
}
