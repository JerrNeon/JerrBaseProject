package com.cw.andoridmvp.retrofit;

import android.content.Context;
import android.widget.Toast;

import com.cw.andoridmvp.net.OkHttpErrorHelper;
import com.cw.andoridmvp.net.XaResult;
import com.cw.andoridmvp.util.NLogUtil;
import com.google.gson.internal.$Gson$Types;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Retrofit 请求回调信息处理)
 * @create by: chenwei
 * @date 2016/10/9 17:11
 */
public abstract class HttpResultCallback<T> {

    private Context mContext;
    /**
     * 泛型类型
     */
    private Type mType;
    /**
     * 加载框
     */
    private KProgressHUD mHud;

    public HttpResultCallback(Context mContext) {
        this.mContext = mContext;
        this.mType = getSuperclassTypeParameter(getClass());
        mHud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
    }

    public abstract void onSuccess(T result);

    public void onError(Throwable e) {

    }

    /**
     * 显示加载对话框
     */
    public void showDialog() {
        mHud.show();
    }

    /**
     * 隐藏加载对话框
     */
    public void dismissDialog() {
        mHud.dismiss();
    }

    /**
     * 处理失败异常错误信息
     *
     * @param result   服务器返回结果信息
     * @param e        异常
     * @param callback
     */
    public void sendFailResultCallback(final XaResult result, final Throwable e, HttpResultCallback callback) {
        NLogUtil.logE("onFailure 异常", e.toString());
        callback.onError(e);
        Toast.makeText(mContext, OkHttpErrorHelper.getMessage(result, e, mContext), Toast.LENGTH_SHORT).show();
    }

    public Type getType() {
        return mType;
    }

    /**
     * 获取泛型类型
     *
     * @param subclass
     * @return
     */
    private Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized
                .getActualTypeArguments()[0]);
    }

}
