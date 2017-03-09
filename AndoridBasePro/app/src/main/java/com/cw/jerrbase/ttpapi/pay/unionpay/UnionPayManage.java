package com.cw.jerrbase.ttpapi.pay.unionpay;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.common.Config;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.unionpay.UPPayAssistEx;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 银联支付
 */
public class UnionPayManage implements Callback, Runnable {

    private static final int PLUGIN_VALID = 0;//插件可用
    private static final int PLUGIN_NOT_INSTALLED = -1;//插件未导入
    private static final int PLUGIN_NEED_UPGRADE = 2;//插件更新

    // 流水号获取地址
    private static final String TN_URL_01 = "http://202.101.25.178:8080/sim/gettn";

    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    private String mMode = "00";

    private static UnionPayManage instance = null;
    private Activity mContext;
    private Handler mHandler = null;
    private KProgressHUD mHUD = null;

    private UnionPayManage(Activity mContext) {
        this.mContext = mContext;
        this.mHandler = new Handler(this);
        mHUD = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
    }

    public static UnionPayManage getInstance(Activity context) {
        if (instance == null) {
            synchronized (UnionPayManage.class) {
                if (instance == null)
                    instance = new UnionPayManage(context);
            }
        }
        return instance;
    }

    /**
     * 网络获取交易流水号后进行支付(TN)
     */
    public void startPay() {
        mHUD.show();
        new Thread(UnionPayManage.this).start();
    }

    /**
     * 外部获取流水号后进行支付
     *
     * @param tn
     */
    public void setTnAndStartPay(String tn) {
        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);
    }

    /**
     * 测试的时候采用此方法，从指定网站获取流水号
     */
    @Override
    public void run() {
        String tn = null;
        InputStream is;
        try {
            URL myURL = new URL(TN_URL_01);
            URLConnection ucon = myURL.openConnection();
            ucon.setConnectTimeout(120000);
            is = ucon.getInputStream();
            int i = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            tn = baos.toString();
            is.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Message msg = mHandler.obtainMessage();
        msg.obj = tn;
        mHandler.sendMessage(msg);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (BuildConfig.LOG_DEBUG)
            Log.i(Config.PAY, msg.obj + "");
        if (mHUD.isShowing())
            mHUD.dismiss();
        if (msg.obj == null || ((String) msg.obj).length() == 0) {
            new AlertDialog.Builder(mContext)
                    .setTitle("错误提示")
                    .setMessage("网络连接失败,请重试!?")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startPay();//重新开始支付
                        }
                    }).create().show();
        } else {
            //通过银联工具类启动支付插件
            doStartUnionPayPlugin(mContext, (String) msg.obj, mMode);
        }

        return false;
    }

    /**
     * @param activity
     * @param tn
     * @param mode     0 - 启动银联正式环境,1 - 连接银联测试环境
     */
    private void doStartUnionPayPlugin(Activity activity, String tn, String mode) {
        int ret = UPPayAssistEx.startPay(activity, null, null, tn, mode);
        if (ret == PLUGIN_NEED_UPGRADE || ret == PLUGIN_NOT_INSTALLED) {
            // 需要重新安装控件
            if (BuildConfig.LOG_DEBUG)
                Log.e(Config.PAY, " plugin not found or need upgrade!!!");
            new AlertDialog.Builder(mContext)
                    .setTitle("提示")
                    .setMessage("完成购买需要安装银联支付控件，是否安装？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 目前使用的内置在assets文件夹中的apk，如果不考虑版本问题，应该使用下载链接
                            UPPayAssistEx.installUPPayPlugin(mContext);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .create().show();
        }
        if (BuildConfig.LOG_DEBUG)
            Log.e(Config.PAY, ret + "");
    }

}
