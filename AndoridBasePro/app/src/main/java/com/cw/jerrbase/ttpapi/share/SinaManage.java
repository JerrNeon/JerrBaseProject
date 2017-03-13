package com.cw.jerrbase.ttpapi.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.common.Config;
import com.cw.jerrbase.ttpapi.TtpConstants;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (新浪登录、分享管理)
 * @create by: chenwei
 * @date 2017/3/9 14:49
 */
public class SinaManage implements WeiboAuthListener, IWeiboHandler.Response {

    private static SinaManage sSinaManage = null;
    private Context mContext = null;
    private SsoHandler mSsoHandler = null;//授权关键类(登录仅用到此类)
    private IWeiboShareAPI mIWeiboShareAPI = null;//分享关键类
    private AuthInfo mAuthInfo = null;
    private SinaResultListener mSinaResultListener = null;//登录、分享结果监听

    public static synchronized SinaManage getInstance(Context context) {
        if (sSinaManage == null)
            sSinaManage = new SinaManage(context.getApplicationContext());
        return sSinaManage;
    }

    private SinaManage(Context context) {
        this.mContext = context;
    }

    /**
     * 分享时在onCreate()方法中调用
     *
     * @param savedInstanceState
     * @param intent
     */
    public void onCreate(@Nullable Bundle savedInstanceState, Intent intent) {
        if (mAuthInfo == null)
            mAuthInfo = new AuthInfo(mContext, TtpConstants.SINA_APP_KEY, TtpConstants.SINA_REDIRECT_URL, TtpConstants.SINA_SCOPE);
        mIWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mContext, TtpConstants.SINA_APP_KEY);
        mIWeiboShareAPI.registerApp();//将应用注册到微博客户端
        if (savedInstanceState != null)
            handleWeiboResponse(intent);
    }

    /**
     * 新浪微博登录
     *
     * @param listener 结果监听
     */
    public void login(@NonNull Activity context, @NonNull SinaResultListener listener) {
        if (mAuthInfo == null)
            mAuthInfo = new AuthInfo(mContext, TtpConstants.SINA_APP_KEY, TtpConstants.SINA_REDIRECT_URL, TtpConstants.SINA_SCOPE);
        mSsoHandler = new SsoHandler(context, mAuthInfo);
        mSinaResultListener = listener;
        mSsoHandler.authorize(this);
    }

    /**
     * 新浪微博分享
     *
     * @param listener 结果监听
     */
    public void share(@NonNull Activity context, @NonNull SinaResultListener listener) {
        if (mIWeiboShareAPI == null) return;
        mSinaResultListener = listener;
        sendMultiMessage(context, true, false, false, false, false, false);
    }

    /**
     * 新浪微博分享内容
     *
     * @param hasText
     * @param hasImage
     * @param hasWebpage
     * @param hasMusic
     * @param hasVideo
     * @param hasVoice
     */
    private void sendMultiMessage(@NonNull Activity context, boolean hasText, boolean hasImage, boolean hasWebpage, boolean hasMusic, boolean hasVideo, boolean hasVoice) {
        WeiboMultiMessage message = new WeiboMultiMessage();
        if (hasText) {
            TextObject textObject = new TextObject();
            textObject.text = "分享内容";
            message.textObject = textObject;
        }
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = message;
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mContext);
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }
        mIWeiboShareAPI.sendRequest(context, request, mAuthInfo, token, this);
    }

    /**
     * 授权时在Activity中onActivityResult()方法中调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void authorizeCallBack(int requestCode, int resultCode, Intent data) {
        if (mSsoHandler != null)
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
    }

    /**
     * 分享时在Activity中onNewIntent()方法中调用
     *
     * @param intent
     */
    public void handleWeiboResponse(Intent intent) {
        if (mIWeiboShareAPI != null)
            mIWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    //登录
    @Override
    public void onComplete(Bundle bundle) {
        // 从 Bundle 中解析 Token
        Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
        if (mAccessToken.isSessionValid()) {
            // 保存 Token 到 SharedPreferences
            AccessTokenKeeper.writeAccessToken(mContext, mAccessToken);
            if (mSinaResultListener != null)
                mSinaResultListener.onSuccess();
        } else {
            // 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
            String code = bundle.getString("code", "");
        }
        if (BuildConfig.LOG_DEBUG)
            Log.i(Config.TAG, "onComplete: ");
    }

    @Override
    public void onWeiboException(WeiboException e) {
        if (BuildConfig.LOG_DEBUG)
            Log.i(Config.TAG, "onWeiboException: " + e.getMessage());
        if (mSinaResultListener != null)
            mSinaResultListener.onFailure();
    }

    @Override
    public void onCancel() {
        if (BuildConfig.LOG_DEBUG)
            Log.i(Config.TAG, "onCancel: ");
    }

    //分享
    @Override
    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.TAG, "onResponse: ok");
                if (mSinaResultListener != null)
                    mSinaResultListener.onSuccess();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.TAG, "onResponse: cancel");
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.TAG, "onResponse: fail");
                if (mSinaResultListener != null)
                    mSinaResultListener.onFailure();
                break;
            default:
                break;
        }
    }

    public interface SinaResultListener {
        void onSuccess();

        void onFailure();
    }
}
