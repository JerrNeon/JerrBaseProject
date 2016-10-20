package com.cw.andoridmvp.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


/**
 * 封装了ScrollView的下拉刷新
 * 
 * @author Li Hong
 * @since 2013-8-22
 */
public class PullToRefreshCustomScrollView extends PullToRefreshBase<CustomScrollView> {

	/**
     * 构造方法
     *
     * @param context context
     */
    public PullToRefreshCustomScrollView(Context context) {
        this(context, null);
    }

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs attrs
     */
    public PullToRefreshCustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public PullToRefreshCustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected CustomScrollView createRefreshableView(Context context, AttributeSet attrs) {
        CustomScrollView scrollView = new CustomScrollView(context,attrs);
        return scrollView;
    }



    @Override
    protected boolean isReadyForPullDown() {
        return mRefreshableView.getScrollY() == 0;
    }

    @Override
    protected boolean isReadyForPullUp() {
        View scrollViewChild = mRefreshableView.getChildAt(0);
        if (null != scrollViewChild) {
            return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        
        return false;
    }
}
