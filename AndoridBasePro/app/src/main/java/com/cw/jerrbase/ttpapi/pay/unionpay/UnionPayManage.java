package com.cw.jerrbase.ttpapi.pay.unionpay;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.common.Config;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * 银联支付
 */
public class UnionPayManage implements Callback, Runnable {

    //Mode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
    private String mMode = UnionPayConstants.UNION_OFFICIAL_CONNECT;

    private static UnionPayManage instance = null;
    private Activity mContext;
    private Handler mHandler = null;
    private KProgressHUD mHUD = null;//加载框
    private UnionPayResultListener mUnionPayResultListener = null;//支付结果监听

    private UnionPayManage(Activity mContext) {
        if (mContext == null)
            this.mContext = mContext;
        if (mHandler == null)
            this.mHandler = new Handler(this);
        if (mHUD == null)
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
    public void startPay(UnionPayResultListener listener) {
        mUnionPayResultListener = listener;
        if (mHUD != null)
            mHUD.show();
        new Thread(UnionPayManage.this).start();
    }

    /**
     * 外部获取流水号后进行支付
     *
     * @param tn 流水号
     */
    public void setTnAndStartPay(String tn, UnionPayResultListener listener) {
        mUnionPayResultListener = listener;
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
            URL myURL = new URL(UnionPayConstants.GET_TN_URL);
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
            Log.i(Config.PAY, "UnionPayTn: " + msg.obj);
        if (mHUD.isShowing())
            mHUD.dismiss();
        if (msg.obj == null || ((String) msg.obj).length() == 0) {
            new AlertDialog.Builder(mContext)
                    .setTitle("错误提示")
                    .setMessage("网络连接失败,请重试!")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

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
        if (BuildConfig.LOG_DEBUG)
            Log.e(Config.PAY, "UnionPayResult: " + ret);
        if (!UPPayAssistEx.checkInstalled(mContext))//是否安装了银联Apk
            return;
        if (ret == UnionPayConstants.PLUGIN_NEED_UPGRADE || ret == UnionPayConstants.PLUGIN_NOT_INSTALLED) {
            // 需要重新安装控件(更新)
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
    }

    /**
     * 支付界面onActivityResult()方法中实现此方法
     * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        String str = data.getExtras().getString("pay_result");
        if (str == null) return;
        if (str.equalsIgnoreCase("success")) {
            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 此处的verify建议送去商户后台做验签
                    // 如要放在手机端验，则代码必须支持更新证书
                    boolean ret = RSAUtil.verify(dataOrg, sign, mMode);
                    if (ret) {
                        // 验签成功，显示支付结果
                        if (BuildConfig.LOG_DEBUG)
                            Log.i(Config.PAY, "onActivityResult: 验签后支付成功");
                        if (mUnionPayResultListener != null)
                            mUnionPayResultListener.onSuccess();
                    } else {
                        // 验签失败
                        if (BuildConfig.LOG_DEBUG)
                            Log.i(Config.PAY, "onActivityResult: 验签后支付失败");
                    }
                } catch (JSONException e) {
                }
            }
            // 结果result_data为成功时，去商户后台查询一下再展示成功
            if (BuildConfig.LOG_DEBUG)
                Log.i(Config.PAY, "onActivityResult: 验签前支付成功");
        } else if (str.equalsIgnoreCase("fail")) {
            if (BuildConfig.LOG_DEBUG)
                Log.i(Config.PAY, "onActivityResult: 支付失败");
            if (mUnionPayResultListener != null)
                mUnionPayResultListener.onFailure();
        } else if (str.equalsIgnoreCase("cancel")) {
            if (BuildConfig.LOG_DEBUG)
                Log.i(Config.PAY, "onActivityResult: 用户取消了支付");
        }
    }

    public interface UnionPayResultListener {
        void onSuccess();

        void onFailure();
    }

}
