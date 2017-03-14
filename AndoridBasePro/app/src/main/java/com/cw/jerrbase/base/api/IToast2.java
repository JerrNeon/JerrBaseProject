package com.cw.jerrbase.base.api;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Toast)
 * @create by: chenwei
 * @date 2017/3/14 14:21
 */
public interface IToast2 extends IToast {

    /**
     * 显示Toast
     *
     * @param message
     * @param duration (Toast.LENGTH_SHORT/Toast.LENGTH_LONG）
     */
    void showToast(String message, int duration);
}
