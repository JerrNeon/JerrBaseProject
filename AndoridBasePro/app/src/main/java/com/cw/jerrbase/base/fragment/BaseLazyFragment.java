package com.cw.jerrbase.base.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cw.jerrbase.base.activity.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (base frament--->带懒加载)
 * @create by: chenwei
 * @date 2016/9/30 11:04
 */
public abstract class BaseLazyFragment extends Fragment {

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
     * Activity对象
     */
    protected Activity mContext;
    /**
     * Fragment对象
     */
    protected Fragment mFragment = null;
    /**
     * fragment布局
     */
    protected View mView;
    /**
     * butterknift操作对象
     */
    protected Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(getLayoutResourceId(), null, false);
        unbinder = ButterKnife.bind(this, mView);
        mContext = getActivity();
        mFragment = this;
        return mView;
    }

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

    /**
     * 初始化EventBus
     */
    protected void initEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 获得Fragment对象
     *
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    public static <T extends Fragment> T newInstance(Class<T> tClass) {
        T fragment = null;
        try {
            fragment = tClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    /**
     * 获得Fragment对象并传递参数
     *
     * @param arguments 要传递的参数
     * @param tClass    传递的目的Fragment的Class对象
     * @param <T>       传递的目的Fragment
     * @return
     */
    public static <T extends Fragment> T newInstance(String arguments, Class<T> tClass) {
        T fragment = null;
        try {
            fragment = tClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (arguments != null) {
            Bundle bundle = new Bundle();
            bundle.putString(tClass.getName(), arguments);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    /**
     * 获取参数
     *
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return 传递的参数
     */
    protected <T extends Fragment> String getParemters(Class<T> tClass) {
        return getArguments().getString(tClass.getName(), "");
    }

    /**
     * 通过类名启动Activity
     *
     * @param cls 需要跳转的类
     */
    protected void openActivity(Class<?> cls) {
        openActivity(cls, null);
    }

    /**
     * 通过类名启动Activity，并且含有Flag标识
     *
     * @param cls  需要跳转的类
     * @param flag 数据
     */
    protected void openActivity(Class<?> cls, int flag) {
        Intent intent = new Intent(mContext, cls);
        intent.setFlags(flag);
        mContext.startActivity(intent);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param cls    需要跳转的类
     * @param bundle 数据
     */
    protected void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param cls    需要跳转的类
     * @param bundle 数据
     */
    protected void openActivity(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据，并会再打开另一个activity
     * 例如：登录成功后需要打开新的activity（@param targetcls）
     *
     * @param cls               需要跳转的类
     * @param bundle            数据
     * @param targetPackageName 要跳转的类的包名
     */
    protected void openActivity(Class<?> cls, String targetPackageName, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtra(BaseActivity.class.getName(), targetPackageName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    protected abstract int getLayoutResourceId();

    protected abstract int sendRequest();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
}
