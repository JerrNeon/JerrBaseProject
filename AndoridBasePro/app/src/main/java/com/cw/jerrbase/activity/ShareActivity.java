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
                                WeChatManage.getInstance(mContext).share(WeChatManage.ShareType.WeiXinFreind);
                                break;
                            case 1:
                                WeChatManage.getInstance(mContext).share(WeChatManage.ShareType.WeiXinCircle);
                                break;
                            case 2:
                                QqManage.getInstance(mContext).shareWithQQ();
                                break;
                            case 3:
                                QqManage.getInstance(mContext).shareWithQzone();
                                break;
                            case 4:
                                SinaManage.getInstance(mContext).share();
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
                QqManage.getInstance(mContext).login();
                break;
            case R.id.tv_weixin:
                WeChatManage.getInstance(mContext).login();
                break;
            case R.id.tv_sina:
                SinaManage.getInstance(mContext).login();
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
