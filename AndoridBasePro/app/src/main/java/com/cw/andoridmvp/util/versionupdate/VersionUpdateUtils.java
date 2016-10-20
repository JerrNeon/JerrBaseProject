package com.cw.andoridmvp.util.versionupdate;

import android.content.Context;
import android.content.Intent;

import com.allenliu.versionchecklib.AVersionService;
import com.allenliu.versionchecklib.VersionParams;
import com.cw.andoridmvp.service.VersionUpdateService;

import java.util.Map;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (版本更新)
 * @create by: chenwei
 * @date 2016/10/11 14:47
 */
public class VersionUpdateUtils {

    /**
     * 检查版本更新(请求方式为post，如果是get请修改本方法)
     *
     * @param mContext
     * @param param         请求参数
     * @param url           请求地址
     * @param isForceUpdate 是否强制更新
     *                      (注：自定service包名,必须填写用于开启service)
     */
    public static void checkUpdate(Context mContext, Map<String, Object> param, String url, boolean isForceUpdate) {
        VersionParams versionField = new VersionParams();
        //是否强制升级,默认false
        versionField.setIsForceUpdate(isForceUpdate);
        //接口请求方式,默认get
        versionField.setRequestMethod(AVersionService.POST);
        //请求参数,选填
        versionField.setRequestParams(param);
        //当版本接口请求失败时，service会根据设定的间隔时间继续请求版本接口，直到手动关闭service或者接口请求成功，不填默认10s
        //versionField.setPauseRequestTime(requestTime);
        //接口地址，必填
        versionField.setRequestUrl(url);
        //自定service包名,必须填写用于开启service
        versionField.setVersionServiceName("com.cw.andoridmvp.service.VersionUpdateService");
        Intent intent = new Intent(mContext, VersionUpdateService.class);
        intent.putExtra("versionField", versionField);
        mContext.startService(intent);
    }
}
