package com.cw.jerrbase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.activity.BaseTbActivity;
import com.cw.jerrbase.common.Constants;
import com.cw.jerrbase.dialog.ShareDialog;
import com.cw.jerrbase.ttpapi.login.IUiListenerIml;
import com.cw.jerrbase.ttpapi.login.IWeiboResponseIml;
import com.cw.jerrbase.ttpapi.login.WeiboAuthListenerIml;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.Tencent;

import java.util.ArrayList;

import butterknife.OnClick;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (友盟登录分享)
 * @create by: chenwei
 * @date 2017/3/7 17:07
 */
public class UMShareActivity extends BaseTbActivity {

    private SsoHandler mSsoHandler;
    private IWeiboShareAPI mIWeiboShareAPI;

    private Tencent mTencent;
    private IUiListenerIml mIUiListener;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_umshare;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("分享和登录");
        setRightIcon(R.drawable.umeng_socialize_more);
        init();
    }

    private void init() {
        mTencent = Tencent.createInstance(Constants.QQ_APP_ID, this.getApplicationContext());
        mIUiListener = new IUiListenerIml();
    }

    @Override
    public void onClickRightImg() {
        super.onClickRightImg();
        ShareDialog.newInstance(ShareDialog.class)
                .show(getSupportFragmentManager(), "share", new ShareDialog.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position) {
                            case 0:
                                shareWithWeixinCircle();
                                break;
                            case 1:
                                shareWithWeixin();
                                break;
                            case 2:
                                shareWithQQ();
                                break;
                            case 3:
                                shareWithQzone();
                                break;
                            case 4:
                                shareWithSina();
                                break;
                            default:
                                break;
                        }
                    }
                });
    }


    @OnClick({R.id.tv_qq, R.id.tv_weixin, R.id.tv_sina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_qq:
                if (!mTencent.isSessionValid())
                    mTencent.login(mContext, "", mIUiListener);
                break;
            case R.id.tv_weixin:
                break;
            case R.id.tv_sina:
                AuthInfo authInfo = new AuthInfo(mContext, Constants.SINA_APP_KEY, Constants.SINA_REDIRECT_URL, Constants.SINA_SCOPE);
                mSsoHandler = new SsoHandler(mContext, authInfo);
                mSsoHandler.authorize(new WeiboAuthListenerIml());
                break;
        }
    }

    /**
     * 微信朋友圈分享
     */
    public void shareWithWeixinCircle() {

    }

    /**
     * 微信分享
     */
    public void shareWithWeixin() {

    }


    /**
     * QQ分享
     */
    public void shareWithQQ() {
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
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
        mTencent.shareToQQ(mContext, params, mIUiListener);
    }

    /**
     * Qzone分享
     */
    public void shareWithQzone() {
        Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://connect.qq.com/");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add("http://f.hiphotos.baidu.com/image/h%3D200/sign=6f05c5f929738bd4db21b531918a876c/6a600c338744ebf8affdde1bdef9d72a6059a702.jpg");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
        mTencent.shareToQzone(mContext, params, mIUiListener);
    }

    /**
     * 新浪微博分享
     */
    private void shareWithSina() {
        mIWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mContext, Constants.SINA_APP_KEY);
        mIWeiboShareAPI.registerApp();//将应用注册到微博客户端
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
        mIWeiboShareAPI.sendRequest(mContext, request);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mIWeiboShareAPI.handleWeiboResponse(intent, new IWeiboResponseIml());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null)
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
    }
}
