package com.cw.jerrbase.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonUtils {
    public static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            GsonBuilder gb = new GsonBuilder();
            gson = gb.create();
        }
        return gson;
    }

    /**
     * 将Java对象转换成json字符串
     * 
     * @param object
     * @return
     */
    public static <T> String toJson(T object) {
        return getGson().toJson(object);
    }

    /**
     * 将json字符串转成JavaBean对象
     * 
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T toObject(String jsonStr, Class<T> clazz) {
        return getGson().fromJson(jsonStr, clazz);
    }

    public static String getObjectForName(final Object json, final String name) {
        String result = null;
        if (json != null) {
            try {
                JSONTokener jsonParser = new JSONTokener(json.toString());
                JSONObject status = (JSONObject) jsonParser.nextValue();
                Object obj = status.get(name);
                if (obj != null) {
                    result = obj.toString();
                }
                jsonParser = null;
                status = null;
            } catch (Throwable e) {
                result = null;
            }
        }
        return result;
    }

}
