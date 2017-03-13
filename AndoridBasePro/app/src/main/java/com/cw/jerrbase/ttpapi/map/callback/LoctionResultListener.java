package com.cw.jerrbase.ttpapi.map.callback;

import com.baidu.location.BDLocation;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (定位结果监听)
 * @create by: chenwei
 * @date 2017/3/13 14:28
 */
public interface LoctionResultListener {

    void onSuccess(BDLocation location);

    void onFailure(BDLocation location);
}
