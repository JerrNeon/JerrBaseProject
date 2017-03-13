package com.cw.jerrbase.base.fragment;

import android.app.Activity;
import android.content.Context;
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
 * @Description: (base frament--->不带懒加载)
 * @create by: chenwei
 * @date 2016/9/30 11:04
 */
public abstract class BaseFragment extends Fragment {

    protected Activity mActivity = null;
    protected Context mContext = null;
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
        mActivity = getActivity();
        mContext = getActivity().getApplicationContext();
        mFragment = this;
        return mView;
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
        Intent intent = new Intent(mActivity, cls);
        intent.setFlags(flag);
        mActivity.startActivity(intent);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param cls    需要跳转的类
     * @param bundle 数据
     */
    protected void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivity(intent);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param cls    需要跳转的类
     * @param bundle 数据
     */
    protected void openActivity(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(mActivity, cls);
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
        Intent intent = new Intent(mActivity, cls);
        intent.putExtra(BaseActivity.class.getName(), targetPackageName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivity(intent);
    }

    protected abstract int getLayoutResourceId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mActivity != null)
            mActivity = null;
        if (mContext != null)
            mContext = null;
        if (mFragment != null)
            mFragment = null;
        if (unbinder != null)
            unbinder.unbind();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }
}
