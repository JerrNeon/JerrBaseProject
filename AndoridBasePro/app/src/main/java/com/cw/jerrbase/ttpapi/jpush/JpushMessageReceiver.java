package com.cw.jerrbase.ttpapi.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.cw.jerrbase.activity.MainActivity;
import com.cw.jerrbase.base.api.IBSRoute;
import com.cw.jerrbase.base.api.ILog;
import com.cw.jerrbase.util.LogUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Jpush消息接收)
 * @create by: chenwei
 * @date 2017/3/14 9:50
 */
public class JpushMessageReceiver extends BroadcastReceiver implements IBSRoute, ILog {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        logI("onReceive - " + intent.getAction());
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            logI("[MyReceiver] 接收Registration Id : " + regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            logI("收到了自定义消息。消息内容是：" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            logI("收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            logI("用户点击打开了通知");
            String type = bundle.getString(JPushInterface.EXTRA_EXTRA);
            logI("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里可以自己写代码去定义用户点击后的行为
            openActivity(context, MainActivity.class);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            String type = bundle.getString(JPushInterface.EXTRA_EXTRA);
            logI("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + type);
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            logW("[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            logW("Unhandled intent - " + intent.getAction());
        }
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }

    @Override
    public void openActivity(@NonNull Context context, @NonNull Class<?> tClass) {
        Intent intent = new Intent(context, tClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Context context, @NonNull Class<?> cls, @NonNull int flag) {

    }

    @Override
    public void openActivity(@NonNull Context context, @NonNull Class<?> cls, @NonNull String param) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(cls.getSimpleName(), param);
        context.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Context context, @NonNull Class<?> cls, @NonNull Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(cls.getSimpleName(), bundle);
        context.startActivity(intent);
    }

    @Override
    public void logI(String message) {
        LogUtils.i(String.format(ILog.messageFormat, getClassName(), message));
    }

    @Override
    public void logW(String message) {
        LogUtils.w(String.format(ILog.messageFormat, getClassName(), message));
    }

    @Override
    public void logE(String message) {
        LogUtils.e(String.format(ILog.messageFormat, getClassName(), message));
    }
}
