package com.cw.jerrbase.activity.other;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.activity.BaseActivity;
import com.cw.jerrbase.util.ImageUtil;
import com.shuyu.gsyvideoplayer.GSYPreViewManager;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import butterknife.BindView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (视频)
 * @create by: chenwei
 * @date 2017/3/6 15:39
 */
public class VideoActivity extends BaseActivity {

    @BindView(R.id.gsy_vieoPlayer)
    StandardGSYVideoPlayer mGsyVieoPlayer;

    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initButterKnife();
        initGsyVideoPlayer();
    }

    private void initGsyVideoPlayer() {
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.bg);
        mGsyVieoPlayer.setThumbImageView(imageView);
        //url
        final String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
        //final String url = "http://180.166.202.80:8080/smgvc/upload/浙江卫视/最美土耳其/最美土耳其宣传片.mp4";
        //设置播放url，第一个url，第二个设置缓存(true为缓存)，第三个设置缓存路径(为空则使用默认的)，第四个设置title
        mGsyVieoPlayer.setUp(url, false, ImageUtil.getVideoCacheFile(), "这是title");
        //非全屏下，不显示title
        mGsyVieoPlayer.getTitleTextView().setVisibility(View.GONE);
        //非全屏下,显示返回键
        mGsyVieoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //打开非全屏下触摸效果
        mGsyVieoPlayer.setIsTouchWiget(true);
        //立即播放
        //mGsyVieoPlayer.startPlayLogic();

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, mGsyVieoPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        mGsyVieoPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        mGsyVieoPlayer.setRotateViewAuto(false);
        mGsyVieoPlayer.setLockLand(false);
        mGsyVieoPlayer.setShowFullAnimation(false);
        mGsyVieoPlayer.setNeedLockFull(true);
        //mGsyVieoPlayer.setOpenPreView(true);
        mGsyVieoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGsyVieoPlayer.isIfCurrentIsFullscreen())
                    return;
                finish();
            }
        });
        mGsyVieoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGsyVieoPlayer.setLockLand(true);
                //直接横屏
                //orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                mGsyVieoPlayer.startWindowFullscreen(mContext, true, true);
            }
        });
        mGsyVieoPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
        mGsyVieoPlayer.setStandardVideoAllCallBack(new StandardVideoAllCallBack() {
            @Override
            public void onClickStartThumb(String s, Object... objects) {

            }

            @Override
            public void onClickBlank(String s, Object... objects) {

            }

            @Override
            public void onClickBlankFullscreen(String s, Object... objects) {

            }

            @Override
            public void onPrepared(String s, Object... objects) {
                //开始播放了才能旋转和全屏
                orientationUtils.setEnable(true);
                isPlay = true;
            }

            @Override
            public void onClickStartIcon(String s, Object... objects) {

            }

            @Override
            public void onClickStartError(String s, Object... objects) {

            }

            @Override
            public void onClickStop(String s, Object... objects) {

            }

            @Override
            public void onClickStopFullscreen(String s, Object... objects) {

            }

            @Override
            public void onClickResume(String s, Object... objects) {

            }

            @Override
            public void onClickResumeFullscreen(String s, Object... objects) {

            }

            @Override
            public void onClickSeekbar(String s, Object... objects) {

            }

            @Override
            public void onClickSeekbarFullscreen(String s, Object... objects) {

            }

            @Override
            public void onAutoComplete(String s, Object... objects) {

            }

            @Override
            public void onEnterFullscreen(String s, Object... objects) {
                //全屏
            }

            @Override
            public void onQuitFullscreen(String s, Object... objects) {
                //退出全屏
            }

            @Override
            public void onQuitSmallWidget(String s, Object... objects) {

            }

            @Override
            public void onEnterSmallWidget(String s, Object... objects) {

            }

            @Override
            public void onTouchScreenSeekVolume(String s, Object... objects) {

            }

            @Override
            public void onTouchScreenSeekPosition(String s, Object... objects) {

            }

            @Override
            public void onTouchScreenSeekLight(String s, Object... objects) {

            }

            @Override
            public void onPlayError(String s, Object... objects) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoPlayer.releaseAllVideos();
        GSYPreViewManager.instance().releaseMediaPlayer();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
                if (!mGsyVieoPlayer.isIfCurrentIsFullscreen()) {
                    mGsyVieoPlayer.startWindowFullscreen(mContext, true, true);
                }
            } else {
                //新版本isIfCurrentIsFullscreen的标志位内部提前设置了，所以不会和手动点击冲突
                if (mGsyVieoPlayer.isIfCurrentIsFullscreen()) {
                    StandardGSYVideoPlayer.backFromWindowFull(this);
                }
                if (orientationUtils != null) {
                    orientationUtils.setEnable(true);
                }
            }
        }
    }
}
