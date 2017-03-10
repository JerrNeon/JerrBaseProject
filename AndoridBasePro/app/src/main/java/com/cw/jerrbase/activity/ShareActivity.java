package com.cw.jerrbase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.activity.BaseTbActivity;
import com.cw.jerrbase.dialog.ShareDialog;
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


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_umshare;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("分享和登录");
        setRightIcon(R.drawable.ic_share);
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
                                WeChatManage.getInstance(mContext).share(WeChatManage.ShareType.WeiXinFriend, new WeChatManage.WeChatResultListener() {
                                    @Override
                                    public void onSuccess(BaseResp resp) {

                                    }

                                    @Override
                                    public void onFailure(BaseResp resp) {

                                    }
                                });
                                break;
                            case 1:
                                WeChatManage.getInstance(mContext).share(WeChatManage.ShareType.WeiXinCircle, new WeChatManage.WeChatResultListener() {
                                    @Override
                                    public void onSuccess(BaseResp resp) {

                                    }

                                    @Override
                                    public void onFailure(BaseResp resp) {

                                    }
                                });
                                break;
                            case 2:
                                QqManage.getInstance(mContext).shareWithQQ(new QqManage.QqResultListener() {
                                    @Override
                                    public void onSuccess(JSONObject response) {

                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });
                                break;
                            case 3:
                                QqManage.getInstance(mContext).shareWithQzone(new QqManage.QqResultListener() {
                                    @Override
                                    public void onSuccess(JSONObject response) {

                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });
                                break;
                            case 4:
                                SinaManage.getInstance(mContext).share(new SinaManage.SinaResultListener() {
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


    @OnClick({R.id.tv_qq, R.id.tv_weixin, R.id.tv_sina})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_qq:
                QqManage.getInstance(mContext).login(new QqManage.QqResultListener() {
                    @Override
                    public void onSuccess(JSONObject response) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
                break;
            case R.id.tv_weixin:
                WeChatManage.getInstance(mContext).login(new WeChatManage.WeChatResultListener() {
                    @Override
                    public void onSuccess(BaseResp resp) {

                    }

                    @Override
                    public void onFailure(BaseResp resp) {

                    }
                });
                break;
            case R.id.tv_sina:
                SinaManage.getInstance(mContext).login(new SinaManage.SinaResultListener() {
                    @Override
                    public void onSuccess() {
                        
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        SinaManage.getInstance(mContext).handleWeiboResponse(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SinaManage.getInstance(mContext).authorizeCallBack(requestCode, resultCode, data);
        QqManage.getInstance(mContext).onActivityResultData(requestCode, resultCode, data);
    }
}
