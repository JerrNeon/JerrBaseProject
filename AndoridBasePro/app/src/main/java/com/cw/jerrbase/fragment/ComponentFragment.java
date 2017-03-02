package com.cw.jerrbase.fragment;

import android.content.Intent;

import com.cw.jerrbase.R;
import com.cw.jerrbase.activity.TabInfoActivity;
import com.cw.jerrbase.base.fragment.BaseFragment;

import butterknife.OnClick;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (组件测试工具类)
 * @create by: chenwei
 * @date 2017/3/1 16:35
 */
public class ComponentFragment extends BaseFragment {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_component;
    }

    @OnClick(R.id.tv_tablayout_okhttp)
    public void TabLayoutAndOkHttp() {
        startActivity(new Intent(mContext, TabInfoActivity.class));
    }
}
