package com.cw.jerrbase.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.cw.jerrbase.common.ActivityManager;
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
public class BaseActivity extends AppCompatActivity {

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";

    protected ActivityManager activityManager = ActivityManager.getActivityManager(this);
    protected Activity mActivity = null;
    protected Context mContext = null;
    /**
     * ButterKnife操作对象
     */
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = getApplicationContext();
        activityManager.putActivity(this);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
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
        if (view != null) return view;
        return super.onCreateView(name, context, attrs);
    }

    /**
     * 初始化ButterKnife
     */
    protected void initButterKnife() {
        unbinder = ButterKnife.bind(this);
    }

    /**
     * 初始化EventBus
     */
    protected void initEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 设置ToolBar
     *
     * @param toolbar
     */
    protected void setToolBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    /**
     * 设置状态栏
     */
    protected void setStatusBar() {

    }

    /**
     * finish掉所有栈里面的activity
     */
    public void exit() {
        activityManager.exit();
        System.exit(0);
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
        Intent intent = new Intent(this, cls);
        intent.setFlags(flag);
        startActivity(intent);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param cls    需要跳转的类
     * @param bundle 数据
     */
    protected void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param cls    需要跳转的类
     * @param bundle 数据
     */
    protected void openActivity(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
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
        Intent intent = new Intent(this, cls);
        intent.putExtra(BaseActivity.class.getName(), targetPackageName);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 只有调用了openActivity(Class<?> cls, String targetPackageName, Bundle bundle)此方法才才有效
     *
     * @param bundle 数据
     */
    protected void openTargetActivity(Bundle bundle) {
        if (getIntent() == null) return;
        Intent intent = null;
        try {
            intent = new Intent(this, Class.forName(getIntent().getStringExtra(BaseActivity.class.getName())));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
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
    }

}
