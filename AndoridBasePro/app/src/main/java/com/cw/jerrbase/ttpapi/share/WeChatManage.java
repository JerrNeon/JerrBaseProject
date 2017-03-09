package com.cw.jerrbase.ttpapi.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.cw.jerrbase.R;
import com.cw.jerrbase.bean.WeChatAccessTokenVO;
import com.cw.jerrbase.bean.WeChatUserInfoVo;
import com.cw.jerrbase.net.callback.ResultCallback;
import com.cw.jerrbase.net.request.OkHttpRequest;
import com.cw.jerrbase.ttpapi.TtpConstants;
import com.cw.jerrbase.util.ToastUtil;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.WeakHashMap;

import static com.cw.jerrbase.ttpapi.share.WeChatManage.ShareType.WeiXinFreind;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (微信登录、分享管理)
 * @create by: chenwei
 * @date 2017/3/9 13:55
 */
public class WeChatManage {

    private static WeChatManage sWeChatManage = null;
    private Context mContext;
    private IWXAPI mIWXAPI = null;

    public static enum ShareType {
        WeiXinFreind, WeiXinCircle
    }

    public static WeChatManage getInstance(Context context) {
        if (sWeChatManage == null) {
            synchronized (WeChatManage.class) {
                if (sWeChatManage == null)
                    sWeChatManage = new WeChatManage(context);
            }
        }
        return sWeChatManage;
    }

    private WeChatManage(Context context) {
        this.mContext = context;
    }

    public void login() {
        mIWXAPI = WXAPIFactory.createWXAPI(mContext, TtpConstants.WECHAT_APP_ID, true);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";//应用授权作用域
        req.state = "wechat_sdk_demo_test";//用于保持请求和回调的状态，授权请求后原样带回给第三方。可设置为简单的随机数加session进行校验
        mIWXAPI.sendReq(req);
    }

    /**
     * 微信分享
     *
     * @param type 0:好友分享  1：朋友圈分享
     */
    public void share(ShareType type) {
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(mContext, TtpConstants.WECHAT_APP_ID, true);
        iwxapi.registerApp(TtpConstants.WECHAT_APP_ID);

        if (!iwxapi.isWXAppInstalled())
            ToastUtil.showToast(mContext, "您手机未安装微信客户端");

        WXWebpageObject wxWebpageObject = new WXWebpageObject();//网页地址分享
        wxWebpageObject.webpageUrl = "url";

        WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
        wxMediaMessage.title = "标题";
        wxMediaMessage.description = "描述";
        wxMediaMessage.setThumbImage(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher));//不能超过32k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        if (type == WeiXinFreind)
            req.scene = SendMessageToWX.Req.WXSceneSession;//发送到微信好友(默认)
        else {
            if (iwxapi.getWXAppSupportAPI() >= 0x21020001)
                req.scene = SendMessageToWX.Req.WXSceneTimeline;//发送到朋友圈
            else
                ToastUtil.showToast(mContext, "微信版本太低,不能分享到朋友圈");
        }
        req.transaction = String.valueOf(System.currentTimeMillis());//用于唯一标识一个请求
        req.message = wxMediaMessage;

        iwxapi.sendReq(req);
    }

    public void handleIntent(Intent intent, IWXAPIEventHandler iwxapiEventHandler) {
        mIWXAPI.handleIntent(intent, iwxapiEventHandler);
    }

    /**
     * 获取access_token
     *
     * @param baseResp
     */
    private void getAccessToken(BaseResp baseResp) {
        WeakHashMap<String, String> params = new WeakHashMap<>();
        params.put("appid", TtpConstants.WECHAT_APP_ID);//应用唯一标识，在微信开放平台提交应用审核通过后获得
        params.put("secret", TtpConstants.WECHAT_SECRET);//应用密钥AppSecret，在微信开放平台提交应用审核通过后获得
        params.put("code", ((SendAuth.Resp) baseResp).code);
        params.put("grant_type", "authorization_code");//填authorization_code
        new OkHttpRequest.Builder().params(params).url(WeChatURL.GET_ACCESS_TOKEN).get(new ResultCallback<WeChatAccessTokenVO>(mContext, WeChatURL.GET_ACCESS_TOKEN) {

            @Override
            public void onResponse(WeChatAccessTokenVO response) {

            }
        });
    }

    /**
     * 刷新access_token
     *
     * @param weChatAccessTokenVO
     */
    private void refreshAccessToken(WeChatAccessTokenVO weChatAccessTokenVO) {
        WeakHashMap<String, String> params = new WeakHashMap<>();
        params.put("appid", TtpConstants.WECHAT_APP_ID);//应用唯一标识，在微信开放平台提交应用审核通过后获得
        params.put("refresh_token", weChatAccessTokenVO.getRefresh_token());//通过access_token获取到的refresh_token参数
        params.put("grant_type", "refresh_token");//填refresh_token
        new OkHttpRequest.Builder().params(params).url(WeChatURL.REFRESH_ACCESS_TOKEN).get(new ResultCallback<WeChatAccessTokenVO>(mContext, WeChatURL.REFRESH_ACCESS_TOKEN) {

            @Override
            public void onResponse(WeChatAccessTokenVO response) {

            }
        });
    }

    /**
     * 检查access_token是否可用
     *
     * @param weChatAccessTokenVO
     */
    private void checkAccessToken(WeChatAccessTokenVO weChatAccessTokenVO) {
        WeakHashMap<String, String> params = new WeakHashMap<>();
        params.put("access_token", weChatAccessTokenVO.getAccess_token());//调用接口凭证
        params.put("openid", weChatAccessTokenVO.getOpenid());//普通用户标识，对该公众帐号唯一
        new OkHttpRequest.Builder().params(params).url(WeChatURL.CHECK_ACCESS_TOKEN).get(new ResultCallback<WeChatAccessTokenVO>(mContext, WeChatURL.CHECK_ACCESS_TOKEN) {

            @Override
            public void onResponse(WeChatAccessTokenVO response) {

            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param weChatAccessTokenVO
     */
    public void getUserInfo(WeChatAccessTokenVO weChatAccessTokenVO) {
        WeakHashMap<String, String> params = new WeakHashMap<>();
        params.put("access_token", weChatAccessTokenVO.getAccess_token());//调用接口凭证
        params.put("openid", weChatAccessTokenVO.getOpenid());//普通用户标识，对该公众帐号唯一
        new OkHttpRequest.Builder().params(params).url(WeChatURL.GET_USER_INFO).get(new ResultCallback<WeChatUserInfoVo>(mContext, WeChatURL.GET_USER_INFO) {

            @Override
            public void onResponse(WeChatUserInfoVo response) {

            }
        });
    }
}
