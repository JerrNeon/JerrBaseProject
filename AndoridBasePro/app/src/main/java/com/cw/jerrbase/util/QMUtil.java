package com.cw.jerrbase.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (数据操作类)
 * @create by: chenwei
 * @date 2016/10/10 18:34
 */
public class QMUtil {

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if (pObj.equals(""))
            return true;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null)
            return false;
        if (pObj.equals(""))
            return false;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return false;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return false;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两个字符串是否相同(顺序一定是一样的)
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isIncludeAllChar(String str1, String str2) {
        boolean flag = true;

        if (str1.length() != str2.length()) {
            flag = false;
        } else {
            if (!str1.equals(str2))
                flag = false;
        }
        return flag;
    }

    /**
     * 判断两个字符串是否相同(不管顺序是否一样)
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean isIncludeSameChar(String str1, String str2) {
        boolean flag = true;

        if (str1.length() != str2.length()) {
            flag = false;
        } else {
            char[] str1Arr = str1.toCharArray();
            Arrays.sort(str1Arr);
            char[] str2Arr = str2.toCharArray();
            Arrays.sort(str2Arr);
            for (int i = 0; i < str2Arr.length; i++) {
                if (str2Arr[i] == str1Arr[i]) {
                    continue;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 检查字符串(如果为空，返回"",否则返回原信息)
     *
     * @param str 原字符串
     * @return 返回
     */
    public static String checkStr(String str) {
        if (isEmpty(str))
            return "";
        else
            return str;
    }

    /**
     * 字符串转为double
     *
     * @param str
     * @return
     */
    public static double strToDouble(String str) {
        double result;
        try {
            result = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            result = 0;
        } catch (NullPointerException e) {
            result = 0;
        }
        return result;
    }

    /**
     * 字符串转为float
     *
     * @param str
     * @return
     */
    public static float strToFloat(String str) {
        float result;
        try {
            result = Float.parseFloat(str);
        } catch (NumberFormatException e) {
            result = 0;
        } catch (NullPointerException e) {
            result = 0;
        }
        return result;
    }

    /**
     * 字符串转为int
     *
     * @param str
     * @return
     */
    public static int strToInt(String str) {
        int result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = 0;
        } catch (NullPointerException e) {
            result = 0;
        }
        return result;
    }

    /**
     * 字符串转为long
     *
     * @param str
     * @return
     */
    public static long strToLong(String str) {
        long result;
        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException e) {
            result = 0L;
        } catch (NullPointerException e) {
            result = 0L;
        }
        return result;
    }

}