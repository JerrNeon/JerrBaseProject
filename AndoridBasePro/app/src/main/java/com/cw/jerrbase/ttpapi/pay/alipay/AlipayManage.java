package com.cw.jerrbase.ttpapi.pay.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.cw.jerrbase.BuildConfig;
import com.cw.jerrbase.common.Config;
import com.cw.jerrbase.ttpapi.TtpConstants;

import java.util.Map;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (支付宝支付管理, 真实App里, privateKey等数据严禁放在客户端, 加签过程务必要放在服务端完成)
 * @create by: chenwei
 * @date 2017/3/9 17:17
 */
public class AlipayManage implements Handler.Callback {

    private static final int SDK_PAY_FLAG = 1;//支付
    private static final int SDK_AUTH_FLAG = 2;//授权

    private Activity mContext = null;
    private Handler mHandler = null;
    private AlipayResultListener mAlipayResultListener = null;
    private AlipayType mAlipayType = null;

    /**
     * 支付类型
     */
    public static enum AlipayType {
        PAY, AUTH
    }

    private AlipayManage(Activity context) {
        this.mContext = context;
        mHandler = new Handler(this);
    }

    private void setAlipayType(AlipayType type) {
        this.mAlipayType = type;
    }

    private void setAlipayResultListener(AlipayResultListener listener) {
        this.mAlipayResultListener = listener;
    }

    private void create() {
        if (mAlipayType == null)
            return;
        if (mAlipayType == AlipayType.PAY)
            pay();
        else
            authV2();
    }

    /**
     * 支付
     */
    public void pay() {
        boolean isRsa2 = TtpConstants.ALIPAY_RSA2_PRIVATE.length() > 0;//是否使用rsa2秘钥
        String privateKey = isRsa2 ? TtpConstants.ALIPAY_RSA2_PRIVATE : TtpConstants.ALIPAY_RSA_PRIVATE;
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(TtpConstants.ALIPAY_APPID, isRsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, isRsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.PAY, "AlipayResult：" + result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 授权
     */
    public void authV2() {
        boolean isRsa2 = TtpConstants.ALIPAY_RSA2_PRIVATE.length() > 0;//是否使用rsa2秘钥
        String privateKey = isRsa2 ? TtpConstants.ALIPAY_RSA2_PRIVATE : TtpConstants.ALIPAY_RSA_PRIVATE;
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(TtpConstants.ALIPAY_PID, TtpConstants.ALIPAY_APPID, TtpConstants.ALIPAY_TARGET_ID, isRsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, isRsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(mContext);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SDK_PAY_FLAG: {
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    if (mAlipayResultListener != null)
                        mAlipayResultListener.onSuccess();
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    if (mAlipayResultListener != null)
                        mAlipayResultListener.onFailure();
                }
                break;
            }
            case SDK_AUTH_FLAG: {
                @SuppressWarnings("unchecked")
                AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                String resultStatus = authResult.getResultStatus();

                // 判断resultStatus 为“9000”且result_code
                // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                    // 获取alipay_open_id，调支付时作为参数extern_token 的value
                    // 传入，则支付账户为该授权账户
                    Toast.makeText(mContext, "授权成功", Toast.LENGTH_SHORT).show();
                    if (mAlipayResultListener != null)
                        mAlipayResultListener.onSuccess();
                } else {
                    // 其他状态值则为授权失败
                    Toast.makeText(mContext, "授权失败", Toast.LENGTH_SHORT).show();
                    if (mAlipayResultListener != null)
                        mAlipayResultListener.onFailure();
                }
                if (BuildConfig.LOG_DEBUG)
                    Log.i(Config.LOGIN, String.format("authCode:%s", authResult.getAuthCode()));
                break;
            }
            default:
                break;
        }
        return false;
    }

    public static class Builder {
        private Activity mContext;
        private AlipayResultListener mAlipayResultListener;
        private AlipayType mAlipayType;

        public Builder(Activity context) {
            this.mContext = context;
        }

        public Builder setAlipayType(AlipayType type) {
            this.mAlipayType = type;
            return this;
        }

        public Builder setAlipayResultListener(AlipayResultListener listener) {
            this.mAlipayResultListener = listener;
            return this;
        }

        public AlipayManage build() {
            AlipayManage alipayManage = new AlipayManage(mContext);
            alipayManage.setAlipayType(mAlipayType);
            alipayManage.setAlipayResultListener(mAlipayResultListener);
            alipayManage.create();
            return alipayManage;
        }
    }

    public interface AlipayResultListener {
        void onSuccess();

        void onFailure();
    }
}
