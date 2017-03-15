package com.cw.jerrbase.widget.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (一个View随着另一个View的Y方向移动相应的距离)注：适用于首页,滑动内容时底部导航栏随着ToolBar隐藏或显示
 * @create by: chenwei
 * @date 2017/3/15 17:02
 */
public class FooterBehavior extends CoordinatorLayout.Behavior<View> {

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float scaleY = Math.abs(dependency.getY() / dependency.getHeight());
        child.setTranslationY(child.getHeight() * scaleY);
        return true;
    }
}
