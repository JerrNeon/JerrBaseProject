package com.cw.andoridmvp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/20 10:28
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("argument"), Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("argument","接收到广播1的消息");
        setResultExtras(bundle);
    }
}
