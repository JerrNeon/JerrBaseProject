package com.cw.jerrbase.service;

import com.allenliu.versionchecklib.AVersionService;
import com.cw.jerrbase.util.AppUtils;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (版本更新service)
 * @create by: chenwei
 * @date 2016/10/11 14:43
 */
public class VersionUpdateService extends AVersionService {

    @Override
    public void onResponses(AVersionService service, String response) {
        //解析response中的下载地址和更新信息
        String downloadUrl = null;
        String updateMsg = null;
        if (AppUtils.getVersionCode(this) > 1)
            service.showVersionDialog(downloadUrl, updateMsg);
    }
}
