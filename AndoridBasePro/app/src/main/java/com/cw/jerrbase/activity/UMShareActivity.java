package com.cw.jerrbase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.R;
import com.cw.jerrbase.base.activity.BaseTbActivity;
import com.cw.jerrbase.common.Config;
import com.cw.jerrbase.dialog.ShareDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Map;

import butterknife.OnClick;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (友盟登录分享)
 * @create by: chenwei
 * @date 2017/3/7 17:07
 */
public class UMShareActivity extends BaseTbActivity {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_umshare;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("分享和登录");
        setRightIcon(R.drawable.umeng_socialize_more);
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
                                share(SHARE_MEDIA.WEIXIN_CIRCLE);
                                break;
                            case 1:
                                share(SHARE_MEDIA.WEIXIN);
                                break;
                            case 2:
                                share(SHARE_MEDIA.QQ);
                                break;
                            case 3:
                                share(SHARE_MEDIA.QZONE);
                                break;
                            case 4:
                                share(SHARE_MEDIA.SINA);
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
                login(SHARE_MEDIA.QQ);
                break;
            case R.id.tv_weixin:
                login(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.tv_sina:
                login(SHARE_MEDIA.SINA);
                break;
        }
    }

    private void share(SHARE_MEDIA platform) {
        new ShareAction(this)
                .withText("hello")
                .withMedia(new UMImage(this, R.drawable.ic_launcher))
                .setPlatform(platform)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {
                        //分享开始的回调
                    }

                    @Override
                    public void onResult(SHARE_MEDIA platform) {
                        if (BuildConfig.LOG_DEBUG)
                            Log.d(Config.TAG, "platform" + platform);
                        Toast.makeText(UMShareActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA platform, Throwable throwable) {
                        Toast.makeText(UMShareActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                        if (throwable != null && BuildConfig.LOG_DEBUG) {
                            Log.d("throw", "throw:" + throwable.getMessage());
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        Toast.makeText(UMShareActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
                    }
                }).share();
    }

    private void login(SHARE_MEDIA platform) {
        UMShareAPI.get(this).getPlatformInfo(this, platform, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                //授权开始的回调
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Toast.makeText(mContext, "Authorize succeed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(mContext, "Authorize fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(mContext, "Authorize cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
