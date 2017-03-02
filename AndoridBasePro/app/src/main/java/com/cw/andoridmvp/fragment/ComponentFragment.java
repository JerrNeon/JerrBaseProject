package com.cw.andoridmvp.fragment;

import android.content.Intent;

import com.cw.andoridmvp.R;
import com.cw.andoridmvp.activity.TabInfoActivity;
import com.cw.andoridmvp.base.fragment.BaseFragment;

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
