package com.cw.andoridmvp.retrofit;

import com.cw.andoridmvp.net.XaResult;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Retrofit请求方法)
 * @create by: chenwei
 * @date 2016/10/9 16:11
 */
public interface HttpService {

    @GET()
    Observable<XaResult<Object>> get(@Url String url, @QueryMap Map<String, Object> params);

    @FormUrlEncoded
    @POST()
    Observable<XaResult<Object>> post(@Url String url, @FieldMap Map<String, Object> params);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST()
    Observable<XaResult<Object>> postJson(@Url String url, @Body RequestBody route);

    @Multipart
    @POST()
    Observable<XaResult<Object>> upLoad(@Url String url, @PartMap Map<String, RequestBody> params);

    @Streaming
    @POST
    Observable<ResponseBody> downLoad(@Url String url);
}
