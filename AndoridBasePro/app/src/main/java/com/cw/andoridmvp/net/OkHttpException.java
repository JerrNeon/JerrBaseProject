package com.cw.andoridmvp.net;

/**
 * 服务器返回的错误信息
 * @author tu
 *
 */
public class OkHttpException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OkHttpException() {
		super();
	}

	public OkHttpException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	public OkHttpException(String detailMessage) {
		super(detailMessage);
	}

	public OkHttpException(Throwable throwable) {
		super(throwable);
	}
	
}
