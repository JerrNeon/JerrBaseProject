package com.cw.jerrbase.base.api;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (对话框)
 * @create by: chenwei
 * @date 2017/3/15 14:32
 */
public interface IDialog {

    /**
     * 显示对话框
     *
     * @param message 为空时则不显示提示信息
     */
    void showProgressDialog(String message);

    /**
     * 隐藏对话框
     */
    void dismisssProgressDialog();

}
