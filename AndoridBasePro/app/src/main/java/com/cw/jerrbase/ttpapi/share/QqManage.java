package com.cw.jerrbase.ttpapi.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.api.ILog;
import com.cw.jerrbase.ttpapi.TtpConstants;
import com.cw.jerrbase.util.LogUtils;
import com.cw.jerrbase.util.gson.JsonUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (QQ登录、分享管理)
 * @create by: chenwei
 * @date 2017/3/9 14:20
 */
public class QqManage implements IUiListener, ILog {

    private static QqManage sMQqManage = null;
    private Context mContext = null;
    private Tencent mTencent = null;
    private QqResultListener mQqResultListener = null;//登录、分享结果监听

    public static synchronized QqManage getInstance(Context context) {
        if (sMQqManage == null)
            sMQqManage = new QqManage(context.getApplicationContext());
        return sMQqManage;
    }


    private QqManage(Context context) {
        this.mContext = context;
    }

    private void init() {
        if (mTencent == null)
            mTencent = Tencent.createInstance(TtpConstants.QQ_APP_ID, mContext);
    }

    /**
     * 登录
     *
     * @param activity activity
     * @param listener 结果监听
     */
    public void login(Activity activity, QqResultListener listener) {
        init();
        if (!checkTecentAvailable())
            return;
        mQqResultListener = listener;
        mTencent.login(activity, "", this);
    }

    /**
     * 登录
     *
     * @param fragment fragment
     * @param listener 结果监听
     */
    public void login(Fragment fragment, QqResultListener listener) {
        init();
        if (!checkTecentAvailable())
            return;
        mQqResultListener = listener;
        mTencent.login(fragment, "", this);
    }


    /**
     * 注销
     */
    public void logout() {
        init();
        if (!checkTecentAvailable())
            return;
        mTencent.logout(mContext);
    }

    /**
     * QQ分享
     *
     * @param activity activity
     * @param listener 结果监听
     */
    public void shareWithQQ(Activity activity, QqResultListener listener) {
        init();
        if (!checkTecentAvailable())
            return;
        mQqResultListener = listener;
        Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "我在测试");
        //这条分享消息被好友点击后的跳转URL。
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://connect.qq.com/");
        //分享的图片URL
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
                "http://img3.cache.netease.com/photo/0005/2013-03-07/8PBKS8G400BV0005.jpg");
        //分享的消息摘要，最长50个字
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "测试");
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, mContext.getResources().getString(R.string.app_name));
        mTencent.shareToQQ(activity, params, this);
    }

    /**
     * Qzone分享
     *
     * @param activity activity
     * @param listener 结果监听
     */
    public void shareWithQzone(Activity activity, QqResultListener listener) {
        init();
        if (!checkTecentAvailable())
            return;
        mQqResultListener = listener;
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://connect.qq.com/");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=6f05c5f929738bd4db21b531918a876c/6a600c338744ebf8affdde1bdef9d72a6059a702.jpg");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
        mTencent.shareToQzone(activity, params, this);
    }

    public boolean onActivityResultData(int requestCode, int resultCode, Intent data) {
        return Tencent.onActivityResultData(requestCode, resultCode, data, this);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        init();
        if (!checkTecentAvailable())
            return;
        UserInfo userInfo = new UserInfo(mContext, mTencent.getQQToken());
        userInfo.getUserInfo(this);
    }

    private boolean checkTecentAvailable() {
        if (mTencent == null)
            return false;
        if (mTencent.isSessionValid())
            return false;
        return true;
    }

    @Override
    public void onComplete(Object response) {
        if (null == response) {
            logI("onComplete: 登录失败");
            return;
        }
        JSONObject jsonResponse = (JSONObject) response;
        if (null != jsonResponse && jsonResponse.length() == 0) {
            logI("onComplete: 登录失败");
            return;
        }
        logI("onComplete: " + JsonUtils.toJson(jsonResponse));
        if (mQqResultListener != null)
            mQqResultListener.onSuccess(jsonResponse);
    }

    @Override
    public void onError(UiError uiError) {
        logE( "onError: " + uiError.errorMessage);
        if (mQqResultListener != null)
            mQqResultListener.onFailure();
    }

    @Override
    public void onCancel() {
        logI("onCancel: ");
    }

    @Override
    public void logI(String message) {
        LogUtils.i(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logW(String message) {
        LogUtils.i(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logE(String message) {
        LogUtils.i(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }

    public interface QqResultListener {
        void onSuccess(JSONObject response);

        void onFailure();
    }

}
