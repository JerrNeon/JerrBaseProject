package com.cw.jerrbase.ttpapi.login;

import android.util.Log;

import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.common.Config;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (QQ登录回调)
 * @create by: chenwei
 * @date 2017/3/8 17:33
 */
public class IUiListenerIml implements IUiListener {

    @Override
    public void onComplete(Object o) {
        if (BuildConfig.LOG_DEBUG)
            Log.i(Config.TAG, "onComplete: ");
    }

    @Override
    public void onError(UiError uiError) {
        if (BuildConfig.LOG_DEBUG)
            Log.i(Config.TAG, "onError: ");
    }

    @Override
    public void onCancel() {
        if (BuildConfig.LOG_DEBUG)
            Log.i(Config.TAG, "onCancel: ");
    }

}
