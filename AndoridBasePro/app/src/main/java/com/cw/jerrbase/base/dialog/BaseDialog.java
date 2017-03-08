package com.cw.jerrbase.base.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (baseDialog)
 * @create by: chenwei
 * @date 2017/3/8 11:06
 */
public abstract class BaseDialog extends DialogFragment {

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

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;//底部显示
        params.width = WindowManager.LayoutParams.MATCH_PARENT;//宽度为全屏
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置半透明背景
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉默认标题
        getDialog().setCanceledOnTouchOutside(false);//点击边际不可消失
        getDialog().getWindow().getAttributes().windowAnimations = getAnimationStyle();
        mView = inflater.inflate(getLayoutResourceId(), container);
        mContext = getActivity();
        mFragment = this;
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    protected abstract int getLayoutResourceId();

    protected abstract int getAnimationStyle();

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mContext != null)
            mContext = null;
        if (mFragment != null)
            mFragment = null;
        if (unbinder != null)
            unbinder.unbind();
    }
}
