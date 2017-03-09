package com.cw.jerrbase.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.common.Config;
import com.cw.jerrbase.ttpapi.share.WeChatManage;
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
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

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

    @Override
    public void onReq(BaseReq baseReq) {
        if (BuildConfig.LOG_DEBUG)
            Log.i(Config.TAG, "onReq: ");
    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                    //授权成功
                    if (BuildConfig.LOG_DEBUG)
                        Log.i(Config.TAG, "onResp: 授权成功");
                    //getAccessToken(baseResp);
                } else if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                    //支付成功
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                    //授权取消
                    if (BuildConfig.LOG_DEBUG)
                        Log.i(Config.TAG, "onResp: 取消授权");
                } else if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                    //支付取消
                }
                break;
            default:
                if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                    //授权失败
                    if (BuildConfig.LOG_DEBUG)
                        Log.i(Config.TAG, "onResp: 授权失败");
                } else if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
                    //支付失败
                }
                break;
        }
    }

}
