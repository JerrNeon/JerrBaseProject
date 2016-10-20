package com.cw.andoridmvp.base.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.cw.andoridmvp.common.ActivityManager;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (BaseActivity)
 * @create by: chenwei
 * @date 2016/8/23 11:33
 */
public class BaseActivity extends AppCompatActivity {

    protected ActivityManager activityManager = ActivityManager.getActivityManager(this);
    protected BaseActivity mContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        activityManager.putActivity(this);
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
     * @param cls       需要跳转的类
     * @param bundle    数据
     * @param targetcls 要跳转的类后再跳转的类
     */
    protected void openActivity(Class<?> cls, Class<?> targetcls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityManager.removeActivity(this);
    }

}
