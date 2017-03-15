package com.cw.jerrbase.base.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.cw.jerrbase.base.api.IDialog;
import com.cw.jerrbase.base.api.IFrame;
import com.cw.jerrbase.base.api.ILog1;
import com.cw.jerrbase.base.api.IRoute3;
import com.cw.jerrbase.base.api.IToast1;
import com.cw.jerrbase.base.api.IUtil;
import com.cw.jerrbase.util.LogUtil;
import com.cw.jerrbase.util.QMUtil;
import com.cw.jerrbase.util.ToastUtil;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (baseDialog)
 * @create by: chenwei
 * @date 2017/3/8 11:06
 */
public abstract class BaseDialog extends DialogFragment implements IFrame, IRoute3, ILog1, IToast1, IUtil, IDialog {

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
    /**
     * 加载框
     */
    protected KProgressHUD mHUD = null;

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
        mActivity = getActivity();
        mContext = getActivity().getApplicationContext();
        mFragment = this;
        initButterKnife();
        return mView;
    }

    protected abstract int getLayoutResourceId();

    protected abstract int getAnimationStyle();

    @Override
    public void initButterKnife() {
        unbinder = ButterKnife.bind(this, mView);
    }

    /**
     * 初始化EventBus
     */
    @Override
    public void initEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void setStatusBar() {

    }

    @Override
    public void showProgressDialog(String message) {
        mHUD = KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        if (!checkObject(message))
            mHUD.setLabel(message);
        mHUD.show();
    }

    @Override
    public void dismisssProgressDialog() {
        if (mHUD == null)
            return;
        if (mHUD.isShowing())
            mHUD.dismiss();
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }

    /**
     * 获得Fragment对象
     *
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    public static <T extends Fragment> T newInstance(@NonNull Class<T> tClass) {
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
     * @param param  要传递的参数
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    public static <T extends Fragment> T newInstance(@NonNull Class<T> tClass, @NonNull int param) {
        T fragment = null;
        try {
            fragment = tClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(tClass.getName(), param);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 获得Fragment对象并传递参数
     *
     * @param param  要传递的参数
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    public static <T extends Fragment> T newInstance(@NonNull Class<T> tClass, @NonNull long param) {
        T fragment = null;
        try {
            fragment = tClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putLong(tClass.getName(), param);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 获得Fragment对象并传递参数
     *
     * @param param  要传递的参数
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    public static <T extends Fragment> T newInstance(@NonNull Class<T> tClass, @NonNull String param) {
        T fragment = null;
        try {
            fragment = tClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putString(tClass.getName(), param);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 获得Fragment对象并传递参数
     *
     * @param params 要传递的参数
     * @param tClass 传递的目的Fragment的Class对象
     * @param <T>    传递的目的Fragment
     * @return
     */
    public static <T extends Fragment> T newInstance(@NonNull Class<T> tClass, @NonNull Bundle params) {
        T fragment = null;
        try {
            fragment = tClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putBundle(tClass.getName(), params);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void openActivity(@NonNull Class<?> cls) {
        Intent intent = new Intent(mActivity, cls);
        mActivity.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull int flag) {
        Intent intent = new Intent(mActivity, cls);
        intent.setFlags(flag);
        mActivity.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull long param) {
        Intent intent = new Intent(mActivity, cls);
        intent.putExtra(cls.getSimpleName(), param);
        mActivity.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull String param) {
        Intent intent = new Intent(mActivity, cls);
        intent.putExtra(cls.getSimpleName(), param);
        mActivity.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull Bundle bundle) {
        Intent intent = new Intent(mActivity, cls);
        intent.putExtras(bundle);
        mActivity.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull String targetPackageName, @Nullable Bundle bundle) {
        Intent intent = new Intent(mActivity, cls);
        intent.putExtra(cls.getSimpleName(), targetPackageName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mActivity.startActivity(intent);
    }

    @Override
    public void openTargetActivity(@Nullable Bundle bundle, @NonNull String targetPackageName) {
        Intent intent = null;
        try {
            intent = new Intent(mActivity, Class.forName(targetPackageName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @Nullable Bundle bundle, @NonNull int requestCode) {
        Intent intent = new Intent(mActivity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(@NonNull int requestCode, @NonNull Intent data) {

    }

    @Override
    public int getInt() {
        return getArguments().getInt(getClassName());
    }

    @Override
    public String getString() {
        return getArguments().getString(getClassName());
    }

    @Override
    public long getLong() {
        return getArguments().getLong(getClassName());
    }

    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK_FLAG && data != null)
            onActivityResult(requestCode, data);
    }

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
        if (mHUD != null)
            mHUD = null;
    }

    @Override
    public void logD(String message) {
        LogUtil.d(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logV(String message) {
        LogUtil.v(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logI(String message) {
        LogUtil.i(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logW(String message) {
        LogUtil.w(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logE(String message) {
        LogUtil.e(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void showToast(String message) {
        ToastUtil.showToast(mContext, message);
    }

    @Override
    public void showToast(String message, int duration) {
        ToastUtil.showToast(mContext, message, duration);
    }

    @Override
    public boolean checkObject(Object object) {
        return QMUtil.isEmpty(object);
    }

    @Override
    public String checkStr(String str) {
        return QMUtil.checkStr(str);
    }

    @Override
    public int str2Int(String str) {
        return QMUtil.strToInt(str);
    }

    @Override
    public long str2Long(String str) {
        return QMUtil.strToInt(str);
    }

    @Override
    public float str2Float(String str) {
        return QMUtil.strToInt(str);
    }

    @Override
    public double str2Double(String str) {
        return QMUtil.strToInt(str);
    }

    @Override
    public String object2Str(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double)
            return String.valueOf(object);
        return "";
    }

}
