package com.cw.jerrbase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.cw.jerrbase.R;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (启动页)
 * @create by: chenwei
 * @date 2017/2/16 10:10
 */
public class SplashActivity extends AppCompatActivity implements Handler.Callback {

    private static final int MSG_WHAT = 0x11;
    private Handler mHandler = null;

    private Button splash_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        splash_btn = (Button) findViewById(R.id.splash_btn);
        mHandler = new Handler(this);
        new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                splash_btn.setText(l / 1000L + "");
            }

            @Override
            public void onFinish() {
                mHandler.sendMessage(mHandler.obtainMessage(MSG_WHAT));
            }
        }.start();
    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.what == MSG_WHAT) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
        return false;
    }
}
