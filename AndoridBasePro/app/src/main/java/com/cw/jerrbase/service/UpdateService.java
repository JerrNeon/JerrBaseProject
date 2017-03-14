package com.cw.jerrbase.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.cw.jerrbase.R;
import com.cw.jerrbase.net.XaResult;
import com.cw.jerrbase.net.callback.ResultCallback;
import com.cw.jerrbase.net.request.OkHttpRequest;
import com.cw.jerrbase.util.AutoInstallUtil;
import com.cw.jerrbase.util.ImageUtil;
import com.squareup.okhttp.Request;

import java.io.File;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/11/9 11:31
 */
public class UpdateService extends Service {

    private Notification mNotification;
    private float currentProgress = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        downloadFile(intent.getStringExtra("url"));
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载文件
     *
     * @param downLoadUrl
     */
    private void downloadFile(final String downLoadUrl) {
        final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(getString(R.string.app_name));
        builder.setTicker("开始下载");
        final String downLoadFileName = getResources().getString(R.string.download_name);
        new OkHttpRequest.Builder().destFileDir(ImageUtil.getFileCacheFile().getAbsolutePath()).destFileName(downLoadFileName).url(downLoadUrl).download(new ResultCallback<Object>(this, downLoadUrl) {

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                builder.setContentText(String.format("下载进度:%d%%/100%%", 0));
                currentProgress = 0;
                mNotification = builder.build();
                mNotification.vibrate = new long[]{500, 500};
                mNotification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
                manager.notify(0, mNotification);
            }

            @Override
            public void inProgress(float progress) {
                //计算每百分之5刷新一下通知栏
                float progress2 = progress * 100;
                if (progress2 - currentProgress > 5) {
                    currentProgress = progress2;
                    builder.setContentText(String.format("下载进度:%d%%/100%%", (int) progress2));
                    builder.setProgress(100, (int) progress2, false);
                    manager.notify(0, builder.build());
                }
            }

            @Override
            public void onResponse(Object response) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(new File(ImageUtil.getFileCacheFile().getAbsolutePath() + "/" + downLoadFileName));
                //设置intent的类型
                intent.setDataAndType(uri,
                        "application/vnd.android.package-archive");
                PendingIntent pendingIntent = PendingIntent.getActivity(UpdateService.this, 0, intent, 0);
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);//设置点击后消失
                builder.setContentText("下载完成");
                builder.setProgress(100, 100, false);
                manager.notify(0, builder.build());
                AutoInstallUtil.install(UpdateService.this, ImageUtil.getFileCacheFile().getAbsolutePath() + "/" + downLoadFileName);
            }

            @Override
            public void onError(XaResult<Object> result, Request request, Exception e) {
                currentProgress = 0;
                builder.setContentText("下载失败");
                manager.notify(0, builder.build());
                //EventBus.getDefault().post(new EventBusConfig(EventBusConfig.VERSION_UPDATE_SERVICE));
            }
        });
    }

}
