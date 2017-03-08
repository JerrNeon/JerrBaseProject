package com.cw.jerrbase.ttpapi.login;

import android.util.Log;

import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.common.Config;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (分享请求回调)
 * @create by: chenwei
 * @date 2017/3/8 17:41
 */
public class IWeiboResponseIml implements IWeiboHandler.Response {

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
