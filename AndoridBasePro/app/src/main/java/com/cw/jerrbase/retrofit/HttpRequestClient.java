package com.cw.jerrbase.retrofit;

import com.cw.jerrbase.net.XaResult;
import com.cw.jerrbase.util.NLogUtil;
import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Retrofit请求操作类)
 * @create by: chenwei
 * @date 2016/10/9 17:03
 */
public class HttpRequestClient {

    @SuppressWarnings("unchecked")
    public static void get(final String url, final Map<String, Object> params, final HttpResultCallback callback) {
        HttpService httpService = HttpRequest.getInstance().getRetrofit().create(HttpService.class);
        Observable observable = httpService.get(url, params);
        enque(observable, callback);
    }

    @SuppressWarnings("unchecked")
    public static void post(final String url, final Map<String, Object> params, final HttpResultCallback callback) {
        HttpService httpService = HttpRequest.getInstance().getRetrofit().create(HttpService.class);
        Observable observable = httpService.post(url, params);
        enque(observable, callback);
    }

    @SuppressWarnings("unchecked")
    public static void postJson(final String url, final Map<String, Object> params, final HttpResultCallback callback) {
        HttpService httpService = HttpRequest.getInstance().getRetrofit().create(HttpService.class);
        Gson gson = new Gson();
        Observable observable = httpService.postJson(url, getJsonRequestBody(gson.toJson(params)));
        enque(observable, callback);
    }

    /**
     * 获取RequestBody对象(用户上传json格式的参数)
     *
     * @param jsonStr
     * @return
     */
    @SuppressWarnings("unchecked")
    public static RequestBody getJsonRequestBody(String jsonStr) {
        if (jsonStr != null)
            return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonStr);
        return null;
    }

    /**
     * 获取RequestBody对象(用于上传文件对File的包装)
     *
     * @param file
     * @return
     */
    @SuppressWarnings("unchecked")
    public static RequestBody getFileRequestBody(File file) {
        if (file.exists())
            return RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> void enque(final Observable observable, final HttpResultCallback<T> callback) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<XaResult<Object>, T>() {

                    @Override
                    public T call(XaResult<Object> xaResult) {
                        if (!"0000".equals(xaResult.getRspCode())) {
                            callback.sendFailResultCallback(xaResult, null, callback);
                            return null;
                        } else {
                            Gson gson = new Gson();
                            String result = gson.toJson(xaResult.getData());
                            if (callback.getType() == String.class) {
                                return (T) result;
                            } else {
                                NLogUtil.sysOut("response", result);//打印返回的数据
                                return gson.fromJson(result, callback.getType());
                            }
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        callback.showDialog();//显示加载框
                    }

                    @Override
                    public void onCompleted() {
                        callback.dismissDialog();//隐藏加载框
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.dismissDialog();//隐藏加载框
                        callback.sendFailResultCallback(null, e, callback);
                    }

                    @Override
                    public void onNext(T t) {
                        callback.onSuccess(t);
                    }
                });
    }

}
