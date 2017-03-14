package com.cw.jerrbase.base.api;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (框架)
 * @create by: chenwei
 * @date 2017/3/14 15:00
 */
public interface IFrame3 extends IFrame2 {

    /**
     * 初始化AutoLayout
     *
     * @param context
     * @param attrs
     * @return view
     */
    View initAutoLayout(String name, Context context, AttributeSet attrs);
}
