package com.cw.jerrbase.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cw.jerrbase.base.api.ILog;
import com.cw.jerrbase.ttpapi.share.WeChatManage;
import com.cw.jerrbase.util.LogUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (微信回调)
 * @create by: chenwei
 * @date 2017/3/9 10:30
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler, ILog {

    private static WeChatManage.WeChatResultListener mWeChatResultListener = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        WeChatManage.getInstance(this).handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        WeChatManage.getInstance(this).handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq baseReq) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                    //授权成功
                    logI("onResp: 授权成功");
                } else if (baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
                    //分享成功
                    logI("onResp: 分享成功");
                } else if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                    //支付成功
                    logI("onResp: 支付成功");
                }
                if (mWeChatResultListener != null)
                    mWeChatResultListener.onSuccess(baseResp);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                    //授权取消
                    logI("onResp: 取消授权");
                } else if (baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
                    //分享成功
                    logI("onResp: 取消分享");
                } else if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                    //支付取消
                    logI("onResp: 取消支付");
                }
                break;
            default:
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                    //授权失败
                    logE("onResp: 授权失败");
                } else if (baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
                    //分享失败
                    logE("onResp:分享失败");
                } else if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                    //支付失败
                    logE("onResp:支付失败");
                }
                if (mWeChatResultListener != null)
                    mWeChatResultListener.onFailure(baseResp);
                break;
        }
    }

    public static void setWeChatResultListener(WeChatManage.WeChatResultListener listener) {
        mWeChatResultListener = listener;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWeChatResultListener != null)
            mWeChatResultListener = null;
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
    public String getClassName() {
        return getClass().getSimpleName();
    }
}
