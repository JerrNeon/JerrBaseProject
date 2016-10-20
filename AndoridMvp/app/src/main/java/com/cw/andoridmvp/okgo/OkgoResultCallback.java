package com.cw.andoridmvp.okgo;

import android.content.Context;
import android.support.annotation.Nullable;

import com.cw.andoridmvp.net.XaResult;
import com.cw.andoridmvp.util.ReflectUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import okhttp3.Call;
import okhttp3.Response;

import static com.cw.andoridmvp.util.ReflectUtils.getSuperclassTypeParameter;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/14 10:08
 */
public abstract class OkgoResultCallback<T> extends AbsCallback<T> {

    /**
     * 加载框
     */
    private KProgressHUD mHud;

    public OkgoResultCallback(Context mContext) {
        mHud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        mHud.show();
    }

    @Override
    public void onSuccess(T t, Call call, Response response) {
        onSuccess(t);
    }

    @Override
    public void onCacheSuccess(T t, Call call) {
        super.onCacheSuccess(t, call);
        onSuccess(t);
    }

    @Override
    public T convertSuccess(Response response) throws Exception {
        T t;
        Gson gson = new Gson();
        XaResult<Object> xaResult = gson.fromJson(response.body().string(), XaResult.class);
        if (ReflectUtils.getSuperclassTypeParameter(getClass()) == String.class)
            t = (T) gson.toJson(xaResult.getData());
        else
            t = gson.fromJson(gson.toJson(xaResult.getData()), getSuperclassTypeParameter(getClass()));
        return t;
    }

    @Override
    public void onAfter(@Nullable T t, @Nullable Exception e) {
        super.onAfter(t, e);
        mHud.dismiss();
    }

    public abstract void onSuccess(T t);
}
