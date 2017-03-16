package com.cw.jerrbase.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.cw.jerrbase.base.api.IDialog;
import com.cw.jerrbase.base.api.IFrame2;
import com.cw.jerrbase.base.api.ILog1;
import com.cw.jerrbase.base.api.IRoute3;
import com.cw.jerrbase.base.api.IToast1;
import com.cw.jerrbase.base.api.IUtil;
import com.cw.jerrbase.common.ActivityManager;
import com.cw.jerrbase.ttpapi.jpush.JpushManage;
import com.cw.jerrbase.util.DateUtils;
import com.cw.jerrbase.util.LogUtils;
import com.cw.jerrbase.util.NumberUtils;
import com.cw.jerrbase.util.QMUtil;
import com.cw.jerrbase.util.ToastUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (BaseActivity)
 * @create by: chenwei
 * @date 2016/8/23 11:33
 */
public class BaseActivity extends AppCompatActivity implements IFrame2, IRoute3, ILog1, IToast1, IUtil, IDialog {

    protected ActivityManager activityManager = ActivityManager.getActivityManager(this);
    protected Activity mActivity = null;
    protected Context mContext = null;
    /**
     * ButterKnife操作对象
     */
    protected Unbinder unbinder;
    /**
     * 加载框
     */
    protected KProgressHUD mHUD = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = getApplicationContext();
        activityManager.putActivity(this);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = initAutoLayout(name, context, attrs);
        if (view != null)
            return view;
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void initButterKnife() {
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void initEventBus() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void setStatusBar() {

    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }

    @Override
    public void setToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
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
    public void exit() {
        activityManager.exit();
        System.exit(0);
    }

    @Override
    public View initAutoLayout(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        }
        if (name.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        }
        if (name.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }
        return view;
    }

    @Override
    public void openActivity(@NonNull Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull int flag) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(flag);
        startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull long param) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(cls.getSimpleName(), param);
        startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull String param) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(cls.getSimpleName(), param);
        startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @Nullable Bundle bundle, @NonNull int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 此方法适用于登录界面，登录成功后打开targetPackageName所在的Activity
     *
     * @param cls               需要跳转的类
     * @param targetPackageName 要跳转的类的包名
     * @param bundle            数据
     */
    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull String targetPackageName, @Nullable Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(cls.getSimpleName(), targetPackageName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 根据targetPackageName打开相应的Activity
     *
     * @param bundle            数据
     * @param targetPackageName
     */
    @Override
    public void openTargetActivity(@Nullable Bundle bundle, @NonNull String targetPackageName) {
        Intent intent = null;
        try {
            intent = new Intent(this, Class.forName(targetPackageName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onActivityResult(@NonNull int requestCode, @NonNull Intent data) {

    }

    @Override
    public int getInt() {
        return getIntent().getIntExtra(getClassName(), 0);
    }

    @Override
    public String getString() {
        return getIntent().getStringExtra(getClassName());
    }

    @Override
    public long getLong() {
        return getIntent().getLongExtra(getClassName(), 0);
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK_FLAG && data != null)
            onActivityResult(requestCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JpushManage.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JpushManage.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityManager.removeActivity(this);
        if (mActivity != null)
            mActivity = null;
        if (mContext != null)
            mContext = null;
        if (unbinder != null)
            unbinder.unbind();
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        if (mHUD != null)
            mHUD.dismiss();
    }

    @Override
    public void logD(String message) {
        LogUtils.d(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logV(String message) {
        LogUtils.v(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logI(String message) {
        LogUtils.i(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logW(String message) {
        LogUtils.w(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logE(String message) {
        LogUtils.e(String.format(messageFormat, getClassName(), message));
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
        return QMUtil.strToLong(str);
    }

    @Override
    public float str2Float(String str) {
        return QMUtil.strToFloat(str);
    }

    @Override
    public double str2Double(String str) {
        return QMUtil.strToDouble(str);
    }

    @Override
    public String object2Str(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double)
            return String.valueOf(object);
        return "";
    }

    @Override
    public String formatPrice(String price) {
        return NumberUtils.formatDouble2String(str2Double(price));
    }

    @Override
    public String formatTime(long time) {
        return DateUtils.formateDateLongToString(time);
    }
}
