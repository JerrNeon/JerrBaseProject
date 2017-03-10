package com.cw.jerrbase.ttpapi.pay.alipay;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (支付宝支付信息实体(自己服务器返回))
 * @create by: chenwei
 * @date 2017/3/10 11:38
 */
public class AliPayInfoVO implements Parcelable {

    private String orderNumber;
    private String subject;
    private String body;
    private String price;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderNumber);
        dest.writeString(this.subject);
        dest.writeString(this.body);
        dest.writeString(this.price);
    }

    public AliPayInfoVO() {
    }

    protected AliPayInfoVO(Parcel in) {
        this.orderNumber = in.readString();
        this.subject = in.readString();
        this.body = in.readString();
        this.price = in.readString();
    }

    public static final Parcelable.Creator<AliPayInfoVO> CREATOR = new Parcelable.Creator<AliPayInfoVO>() {
        @Override
        public AliPayInfoVO createFromParcel(Parcel source) {
            return new AliPayInfoVO(source);
        }

        @Override
        public AliPayInfoVO[] newArray(int size) {
            return new AliPayInfoVO[size];
        }
    };
}
