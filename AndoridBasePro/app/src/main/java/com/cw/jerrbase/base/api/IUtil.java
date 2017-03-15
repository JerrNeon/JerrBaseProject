package com.cw.jerrbase.base.api;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (工具)
 * @create by: chenwei
 * @date 2017/3/15 10:47
 */
public interface IUtil {

    /**
     * 检查对象是否为空
     *
     * @param object
     * @return
     */
    boolean checkObject(Object object);

    /**
     * 检查字符串
     *
     * @param str
     * @return 为空则返回"",不为空则返回原数据
     */
    String checkStr(String str);

    /**
     * 字符串转为Int
     *
     * @param str
     * @return 转换失败则返回0
     */
    int str2Int(String str);

    /**
     * @param str
     * @return 转换失败则返回0
     */
    long str2Long(String str);

    /**
     * @param str
     * @return 转换失败则返回0
     */
    float str2Float(String str);

    /**
     * @param str
     * @return 转换失败则返回0
     */
    double str2Double(String str);

    /**
     * 基本类型转为字符串
     *
     * @param object
     * @return
     */
    String object2Str(Object object);
}
