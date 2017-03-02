package com.cw.jerrbase.fragment;

import android.content.Intent;

import com.cw.jerrbase.R;
import com.cw.jerrbase.activity.other.BaiduMapActivity;
import com.cw.jerrbase.activity.other.TabInfoActivity;
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
    public void onTabLayoutAndOkHttp() {
        startActivity(new Intent(mContext, TabInfoActivity.class));
    }

    @OnClick(R.id.tv_baiduMap)
    public void onBaiduMap() {
        startActivity(new Intent(mContext, BaiduMapActivity.class));
    }
}
