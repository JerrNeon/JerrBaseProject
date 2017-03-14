package com.cw.jerrbase.base.api;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Log输出)
 * @create by: chenwei
 * @date 2017/3/14 13:17
 */
public interface ILog extends IBase{

    String messageFormat = "%s---》%s";

    void logI(String message);

    void logW(String message);

    void logE(String message);
}
