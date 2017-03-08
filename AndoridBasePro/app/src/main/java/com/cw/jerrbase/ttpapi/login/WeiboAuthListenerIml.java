package com.cw.jerrbase.ttpapi.login;

import android.os.Bundle;
import android.util.Log;

import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.common.Config;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (微博授权回调)
 * @create by: chenwei
 * @date 2017/3/8 17:35
 */
public class WeiboAuthListenerIml implements WeiboAuthListener {

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
}
