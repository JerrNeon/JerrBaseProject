package com.cw.jerrbase.activity.other;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.activity.BaseTbActivity;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/12/22 13:54
 */
public class AutoLayoutTestActivity extends BaseTbActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("autolayout");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_autolayouttest;
    }
}
