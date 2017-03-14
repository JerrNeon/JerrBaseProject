package com.cw.jerrbase.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (apk管理)
 * @create by: chenwei
 * @date 2016/9/8 14:25
 */
public class AutoInstallUtil {

    /**
     * 安装
     *
     * @param mContext 接收外部传进来的context
     */
    public static void install(Context mContext, String apkPath) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    /**
     * 卸载
     *
     * @param mContext 接收外部传进来的context
     */
    public static void uninstall(Context mContext, String packname) {
        Uri packageURI = Uri.parse("package:" + packname);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        mContext.startActivity(uninstallIntent);
    }
}
