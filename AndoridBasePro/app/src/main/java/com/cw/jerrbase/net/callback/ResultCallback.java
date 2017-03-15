package com.cw.jerrbase.net.callback;

import android.content.Context;
import android.text.TextUtils;

import com.cw.jerrbase.net.OkHttpErrorHelper;
import com.cw.jerrbase.net.XaResult;
import com.google.gson.internal.$Gson$Types;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.okhttp.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public abstract class ResultCallback<T> {

    public Type mType;
    private Context mContext;
    private Object tag1;
    private KProgressHUD hud;

    public Object getTag() {
        return this.tag1;
    }

    public ResultCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * @param mContext
     * @param tag      用于取消请求
     */
    public ResultCallback(Context mContext, final Object tag) {
        this.mContext = mContext;
        mType = getSuperclassTypeParameter(getClass());
        this.tag1 = tag;
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
        //hud.setLabel("加载中")
    }

    /**
     * @param mContext
     * @param isProgress 加载框
     */
    public ResultCallback(Context mContext, boolean isProgress) {
        this.mContext = mContext;
        mType = getSuperclassTypeParameter(getClass());
        if (isProgress)
            hud = KProgressHUD.create(mContext)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f);
        //hud.setLabel("加载中")
    }

    /**
     * @param mContext
     */
    public ResultCallback(Context mContext) {
        this.mContext = mContext;
        mType = getSuperclassTypeParameter(getClass());
        /*mDialog = new ProgressDialog(mContext);
        mDialog.setCanceledOnTouchOutside(false);*/
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public void onBefore(Request request) {
        if (hud != null)
            hud.show();
    }

    public void onAfter() {
        if (hud != null)
            hud.dismiss();
        hud = null;
    }

    public void onUpDataTime(long lastTime) {

    }

    public void inProgress(float progress) {

    }

    public void onError(XaResult<T> result, Request request, Exception e) {
        String errorStr = OkHttpErrorHelper.getMessage(result, e, mContext);
        if (!TextUtils.isEmpty(errorStr)) {
            if (errorStr.equals("请重新登录")) {
                //Utils.destroyUserInfo();
            }
            if ("会话信息不存在".equals(errorStr) && "2007".equals(result.getRspCode())) {
                //Utils.showUserTokenUnuseDialog(mContext);
                return;
            }
            //ToastUtil.showToast(mContext, errorStr);
        }
    }

    public abstract void onResponse(T response);

    public static final ResultCallback<String> DEFAULT_RESULT_CALLBACK = new ResultCallback<String>() {
        @Override
        public void onError(XaResult<String> result, Request request,
                            Exception e) {

        }

        @Override
        public void onResponse(String response) {

        }
    };

    public void setHudLabel(String label) {
        hud.setLabel(label);
    }
}