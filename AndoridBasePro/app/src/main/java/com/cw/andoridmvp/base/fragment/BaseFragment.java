package com.cw.andoridmvp.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (base frament)
 * @create by: chenwei
 * @date 2016/9/30 11:04
 */
public abstract class BaseFragment extends Fragment {

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

    protected abstract int getLayoutResourceId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
