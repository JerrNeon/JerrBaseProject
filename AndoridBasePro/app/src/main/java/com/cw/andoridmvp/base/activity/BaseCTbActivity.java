package com.cw.andoridmvp.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (BaseCTbActivity) toolbar需要自定义
 * @create by: chenwei
 * @date 2016/8/23 11:33
 */
public abstract class BaseCTbActivity extends BaseActivity {

    /**
     * ButterKnife操作对象
     */
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        unbinder = ButterKnife.bind(this);
    }

    protected abstract int getLayoutResourceId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
