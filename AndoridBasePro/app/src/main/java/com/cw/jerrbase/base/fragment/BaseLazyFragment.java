package com.cw.jerrbase.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (base frament--->带懒加载)
 * @create by: chenwei
 * @date 2016/9/30 11:04
 */
public abstract class BaseLazyFragment extends BaseFragment {

    /**
     * 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，
     * 在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
     */
    protected boolean isPrepared;
    /**
     * 标志当前页面是否可见
     */
    protected boolean isVisible;
    /**
     * 是否已经加载过
     */
    protected boolean isLoad;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //懒加载
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected abstract void sendRequest();

    /**
     * 页面可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 页面不可见
     */
    protected void onInvisible() {
    }

    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isVisible || !isPrepared)
            return;
        sendRequest();//数据请求
    }

}
