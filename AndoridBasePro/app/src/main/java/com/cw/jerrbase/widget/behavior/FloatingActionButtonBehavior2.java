package com.cw.jerrbase.widget.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (上滑显示 FloatingActionButton, 下滑隐藏 FloatingActionButton)
 * @create by: chenwei
 * @date 2017/3/15 16:17
 */
public class FloatingActionButtonBehavior2 extends FloatingActionButton.Behavior {

    public FloatingActionButtonBehavior2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed < -10 && child.getVisibility() == View.VISIBLE)
            child.hide();
        else if (dyConsumed > 10 && child.getVisibility() != View.VISIBLE)
            child.show();
    }
}
