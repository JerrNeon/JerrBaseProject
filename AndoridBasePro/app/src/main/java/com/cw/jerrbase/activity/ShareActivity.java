package com.cw.jerrbase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.R;
import com.cw.jerrbase.base.activity.BaseTbActivity;
import com.cw.jerrbase.common.Config;
import com.cw.jerrbase.dialog.ShareDialog;
import com.cw.jerrbase.ttpapi.pay.alipay.AlipayManage;
import com.cw.jerrbase.ttpapi.pay.unionpay.UnionPayManage;
import com.cw.jerrbase.ttpapi.pay.wxpay.WxPayInfoVO;
import com.cw.jerrbase.ttpapi.share.QqManage;
import com.cw.jerrbase.ttpapi.share.SinaManage;
import com.cw.jerrbase.ttpapi.share.WeChatManage;
import com.tencent.mm.opensdk.modelbase.BaseResp;

import org.json.JSONObject;

import butterknife.OnClick;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (友盟登录分享)
 * @create by: chenwei
 * @date 2017/3/7 17:07
 */
public class ShareActivity extends BaseTbActivity {

    private WeChatManage mWeChatManage = null;
    private QqManage mQqManage = null;
    private SinaManage mSinaManage = null;
    private AlipayManage mAlipayManage = null;
    private UnionPayManage mUnionPayManage = null;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_umshare;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("分享、登录和支付");
        setRightIcon(R.drawable.ic_share);
        init();
        mSinaManage.onCreate(savedInstanceState, getIntent());
    }

    private void init() {
        mWeChatManage = WeChatManage.getInstance(mContext);
        mQqManage = QqManage.getInstance(mContext);
        mSinaManage = SinaManage.getInstance(mContext);
        mAlipayManage = AlipayManage.getInstance(mContext);
        mUnionPayManage = UnionPayManage.getInstance();
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
                                mWeChatManage.share(mActivity,WeChatManage.ShareType.WeiXinFriend, new WeChatManage.WeChatResultListener() {
                                    @Override
                                    public void onSuccess(BaseResp resp) {

                                    }

                                    @Override
                                    public void onFailure(BaseResp resp) {

                                    }
                                });
                                break;
                            case 1:
                                mWeChatManage.share(mActivity,WeChatManage.ShareType.WeiXinCircle, new WeChatManage.WeChatResultListener() {
                                    @Override
                                    public void onSuccess(BaseResp resp) {

                                    }

                                    @Override
                                    public void onFailure(BaseResp resp) {

                                    }
                                });
                                break;
                            case 2:
                                mQqManage.shareWithQQ(mActivity, new QqManage.QqResultListener() {
                                    @Override
                                    public void onSuccess(JSONObject response) {

                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });
                                break;
                            case 3:
                                mQqManage.shareWithQzone(mActivity, new QqManage.QqResultListener() {
                                    @Override
                                    public void onSuccess(JSONObject response) {

                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });
                                break;
                            case 4:
                                mSinaManage.share(mActivity, new SinaManage.SinaResultListener() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });
                                break;
                            default:
                                break;
                        }
                    }
                });
    }


    @OnClick({R.id.tv_qq, R.id.tv_weixin, R.id.tv_sina, R.id.tv_weChatPay, R.id.tv_aliPay, R.id.tv_unionPay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_qq:
                mQqManage.login(mActivity, new QqManage.QqResultListener() {
                    @Override
                    public void onSuccess(JSONObject response) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
                break;
            case R.id.tv_weixin:
                mWeChatManage.login(mActivity,new WeChatManage.WeChatResultListener() {
                    @Override
                    public void onSuccess(BaseResp resp) {

                    }

                    @Override
                    public void onFailure(BaseResp resp) {

                    }
                });
                break;
            case R.id.tv_sina:
                mSinaManage.login(mActivity, new SinaManage.SinaResultListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
                break;
            case R.id.tv_weChatPay:
                WxPayInfoVO wxPayInfoVO = new WxPayInfoVO();
                mWeChatManage.pay(mActivity,wxPayInfoVO, new WeChatManage.WeChatResultListener() {
                    @Override
                    public void onSuccess(BaseResp resp) {
                        if (BuildConfig.LOG_DEBUG)
                            Log.i(Config.PAY, "onSuccess: ");
                    }

                    @Override
                    public void onFailure(BaseResp resp) {
                        if (BuildConfig.LOG_DEBUG)
                            Log.i(Config.PAY, "onFailure: ");
                    }
                });
                break;
            case R.id.tv_aliPay:
                mAlipayManage.pay(mActivity, new AlipayManage.AlipayResultListener() {
                    @Override
                    public void onSuccess() {
                        if (BuildConfig.LOG_DEBUG)
                            Log.i(Config.PAY, "onSuccess: ");
                    }

                    @Override
                    public void onFailure() {
                        if (BuildConfig.LOG_DEBUG)
                            Log.i(Config.PAY, "onFailure: ");
                    }
                });
                break;
            case R.id.tv_unionPay:
                mUnionPayManage.startPay(mActivity, new UnionPayManage.UnionPayResultListener() {
                    @Override
                    public void onSuccess() {
                        if (BuildConfig.LOG_DEBUG)
                            Log.i(Config.PAY, "onSuccess: ");
                    }

                    @Override
                    public void onFailure() {
                        if (BuildConfig.LOG_DEBUG)
                            Log.i(Config.PAY, "onFailure: ");
                    }
                });
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mSinaManage.handleWeiboResponse(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mSinaManage.authorizeCallBack(requestCode, resultCode, data);
        mQqManage.onActivityResultData(requestCode, resultCode, data);
        mUnionPayManage.onActivityResult(requestCode, resultCode, data);
    }
}
