package com.cw.jerrbase.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cw.jerrbase.R;
import com.cw.jerrbase.util.LogUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;



/**
 * 错误异常帮助类
 *
 * @author tu
 * @since 2015/9/29
 */
public class OkHttpErrorHelper {
	public static <T> String getMessage(XaResult<T> result, Object error,
										Context context) {
		if (!isNetworkConnected(context)) { // 无网络连接
			return context.getResources().getString(R.string.no_internet);
		} else if (error instanceof OkHttpException) {
			if (result == null) {
				return context.getResources().getString(   //可能是连接服务器失败
						R.string.generic_server_down);
			} else {
				return result.getRspDesc();    //服务器返回错误信息
			}
		} else if (error instanceof ConnectException) {
			return context.getResources().getString(
					R.string.generic_server_down);   //可能是连接服务器失败
		} else if (error instanceof SocketTimeoutException) {
			return context.getResources().getString(R.string.connect_time_out);  //连接超时
		}else if(error instanceof com.google.gson.JsonParseException){
			LogUtils.e("OkHttpErrorHelper--->" + context.getResources().getString(R.string.json_paser_error));
			return "";
		}/*else if(error instanceof JsonPaserNullException){
			NLogUtil.logE("OkHttpErrorHelper" ,context.getResources().getString(R.string.json_paser_null));
			return "";
		}*/else if("java.net.SocketException: Socket closed".equals(error.toString())
			|| "java.io.IOException: Canceled".equals(error.toString())){
			//这里应该是用户取消请求抛出的异常,其他情况可能也会抛出此异常，上面大多数判断可以处理
			LogUtils.e("OkHttpErrorHelper--->" + context.getResources().getString(R.string.user_cancel));
			return "";
		}else if(error instanceof IOException){
			return "";
		}
		return context.getResources().getString(R.string.generic_error);
	}

	/**
	 * 检测网络是否可用
	 *
	 * @return
	 */
	private static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}
}
