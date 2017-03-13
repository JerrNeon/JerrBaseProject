package com.cw.jerrbase.activity.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.activity.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (测试状态栏)
 * @create by: chenwei
 * @date 2016/10/21 11:12
 */
public class ImageStatusActivity extends BaseActivity {

    @BindView(R.id.status_iv)
    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_imagestatus);
        setStatusBar();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(mActivity, 0, iv);
    }
}
