package com.cw.jerrbase.ttpapi;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (第三方平台常量)
 * @create by: chenwei
 * @date 2016/10/9 14:28
 */
public class TtpConstants {

    public static final String QQ_APP_ID = "100424468";//QQ_APP_ID

    public static final String WECHAT_APP_ID = "";//WeChat_APP_ID
    public static final String WECHAT_SECRET = "";//WeChat_SECRET

    public static final String SINA_APP_KEY = "3921700954";//新浪AppKey
    public static final String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";//新浪回调地址(默认地址)
    public static final String SINA_SCOPE = "";//应用申请的高级权限

    public static final String ALIPAY_APPID = "";//支付宝支付业务：入参app_id
    public static final String ALIPAY_PID = "";//支付宝账户登录授权业务：入参pid值
    public static final String ALIPAY_TARGET_ID = "";//支付宝账户登录授权业务：入参target_id值
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String ALIPAY_RSA2_PRIVATE = "";
    public static final String ALIPAY_RSA_PRIVATE = "";

}
