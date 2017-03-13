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

    public static final String WECHAT_APP_ID = "wx28d968fe4bc86b9c";//WeChat_APP_ID
    public static final String WECHAT_SECRET = "2088121453528610";//WeChat_SECRET

    public static final String SINA_APP_KEY = "3921700954";//新浪AppKey
    public static final String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";//新浪回调地址(默认地址)
    public static final String SINA_SCOPE = "";//应用申请的高级权限

    public static final String ALIPAY_APPID = "2088189563528610";//支付宝支付业务：入参app_id
    public static final String ALIPAY_PID = "2088121453528610";//支付宝账户登录授权业务：入参pid值
    public static final String ALIPAY_TARGET_ID = "20881214514228610";//支付宝账户登录授权业务：入参target_id值
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String ALIPAY_RSA2_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKeanODDjeHxIUYbmBOlC1M6QH5wjfU0BhjO71eB5/x7XoA//JNUfsIQbfOFZkZRG8T7MNFSKNSe4DSXVnjYm5WwtJwv4aVsBXUPmFIgHt6W++kjaZypVd6PAHzk5FR1xBlUpM58KMrI8jMqUpmebYFNo33dmaXMOW8MAbQv8Zv5AgMBAAECgYBNeexg/iTdOBEQjnrrQdUNYRUlHbSRj73tw+LhybxKe2EA2hNQq7N41A1vj8/qW14B3bgAWwFi4Bp2VSr06/RnK2mCtXB3OX1H64c8hJ8w9FFiGj9X4q1m1lyZ+jTWOvbMsmf5Rn2vaOBd8fbwbJuQptF9ZzOTq/yh2IhvjLCn8QJBAM8vIhWPGb3Pd2Ak/XdeJniA8cOKvRNwAG6IgoD8lvRoj74xK69UoV5AISPRIxub8ozv7HtD2kSgt28VEfmLDqUCQQDPGBiWZVVWX/W36Gq1AhOyzfYEYnFGpAThslL5yeg/kFINDbhlHoqoSpCfxMhfT46Um11TJnU205u+IaQsAUvFAkAFrlAr8SmOh9LJIxqEHGPHqBl4+CPpFYgdf8a8TLDC8N8IIwcEnrhyAiYmekSRLDyBWs7MLnccrJ96/0Pn6MU1AkB4fgmYP79GMTDzXvvu8xVo/GK+rFRCCJ56ftm+UhaaHStQQwJde0aroi7BdqoqokxP9JF5FrAuRTKhjktJ+zsNAkEAlUkfT0Qa5yY5VJncQcryQXhz0kkum0IF7WVnZksCnpmD3k/xYNZZXf7eezERnA4NBr/y3IP9kX3TmNzJV8fx2w==";
    public static final String ALIPAY_RSA_PRIVATE = "";

}
