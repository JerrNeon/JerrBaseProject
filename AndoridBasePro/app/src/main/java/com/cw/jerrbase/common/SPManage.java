package com.cw.jerrbase.common;

import android.content.Context;

import com.cw.jerrbase.bean.UserInfoVO;
import com.cw.jerrbase.util.SPUtils;
import com.cw.jerrbase.util.gson.JsonUtils;
import com.google.gson.JsonParseException;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (SharePreference管理)
 * @create by: chenwei
 * @date 2017/3/14 17:43
 */
public class SPManage {

    public static final String USER_INFO = "user_info";//用户信息
    public static final String SOUND_ENABLE = "sound_enable";//声音是否可用
    public static final String VIRBATE_ENABLE = "virbate_enable";//震动是否可用
    public static final String MESSAGE_ENABLE = "message_enable";//消息是否可用
    public static final String ALIAS_VALUE = "alias_value";//别名值

    private static SPManage instance = null;
    private Context mContext = null;

    private SPManage(Context context) {
        this.mContext = context;
    }

    public static synchronized SPManage getInstance(Context context) {
        if (instance == null)
            instance = new SPManage(context.getApplicationContext());
        return instance;
    }

    public UserInfoVO getUserInfo() {
        UserInfoVO userInfo = null;
        try {
            userInfo = JsonUtils.toObject((String) SPUtils.get(mContext, USER_INFO, null), UserInfoVO.class);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public void setUserInfo(UserInfoVO userInfo) {
        SPUtils.put(mContext, USER_INFO, JsonUtils.toJson(userInfo));
    }

    public boolean isSoundEnable() {
        return (boolean) SPUtils.get(mContext, SOUND_ENABLE, true);
    }

    public void setSoundEnable(boolean soundEnable) {
        SPUtils.put(mContext, SOUND_ENABLE, soundEnable);
    }

    public boolean isVirbateEnable() {
        return (boolean) SPUtils.get(mContext, VIRBATE_ENABLE, true);
    }

    public void setVirbateEnable(boolean virbateEnable) {
        SPUtils.put(mContext, VIRBATE_ENABLE, virbateEnable);
    }

    public boolean isMessageEnable() {
        return (boolean) SPUtils.get(mContext, MESSAGE_ENABLE, true);
    }

    public void setMessageEnable(boolean messageEnable) {
        SPUtils.put(mContext, MESSAGE_ENABLE, messageEnable);
    }

    public String getAliasValue() {
        return (String) SPUtils.get(mContext, ALIAS_VALUE, "");
    }

    public void setAliasValue(String aliasValue) {
        SPUtils.put(mContext, ALIAS_VALUE, aliasValue);
    }
}
