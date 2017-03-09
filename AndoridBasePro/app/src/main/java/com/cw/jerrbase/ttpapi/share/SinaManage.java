package com.cw.jerrbase.ttpapi.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
    private Activity mContext = null;
    private SsoHandler mSsoHandler = null;
    private IWeiboShareAPI mIWeiboShareAPI = null;

    public static SinaManage getInstance(Activity context) {
        if (sSinaManage == null) {
            synchronized (SinaManage.class) {
                if (sSinaManage == null)
                    sSinaManage = new SinaManage(context);
            }
        }
        return sSinaManage;
    }

    private SinaManage(Activity context) {
        this.mContext = context;
        AuthInfo authInfo = new AuthInfo(context, TtpConstants.SINA_APP_KEY, TtpConstants.SINA_REDIRECT_URL, TtpConstants.SINA_SCOPE);
        mSsoHandler = new SsoHandler(context, authInfo);
        mIWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mContext, TtpConstants.SINA_APP_KEY);
        mIWeiboShareAPI.registerApp();//将应用注册到微博客户端
    }

    public void login() {
        if (mSsoHandler != null)
            mSsoHandler.authorize(this);
    }

    /**
     * 新浪微博分享
     */
    public void share() {
        sendMultiMessage(true, false, false, false, false, false);
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
    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage, boolean hasMusic, boolean hasVideo, boolean hasVoice) {
        WeiboMultiMessage message = new WeiboMultiMessage();
        if (hasText) {
            TextObject textObject = new TextObject();
            textObject.text = "分享内容";
            message.textObject = textObject;
        }
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = message;
        if (mIWeiboShareAPI != null)
            mIWeiboShareAPI.sendRequest(mContext, request);
    }

    public void authorizeCallBack(int requestCode, int resultCode, Intent data) {
        if (mSsoHandler != null)
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
    }

    public void handleWeiboResponse(Intent intent) {
        if (mIWeiboShareAPI != null)
            mIWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    @Override
    public void onComplete(Bundle bundle) {
        // 从 Bundle 中解析 Token
        Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(bundle);
        if (mAccessToken.isSessionValid()) {
            // 保存 Token 到 SharedPreferences
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
    }

    @Override
    public void onCancel() {
        if (BuildConfig.LOG_DEBUG)
            Log.i(Config.TAG, "onCancel: ");
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.TAG, "onResponse: ok");
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.TAG, "onResponse: cancel");
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.TAG, "onResponse: fail");
                break;
            default:
                break;
        }
    }
}
