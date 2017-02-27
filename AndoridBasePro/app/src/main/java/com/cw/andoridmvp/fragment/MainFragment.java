package com.cw.andoridmvp.fragment;

import android.content.Intent;

import com.cw.andoridmvp.R;
import com.cw.andoridmvp.activity.TabInfoActivity;
import com.cw.andoridmvp.base.fragment.BaseFragment;

import butterknife.OnClick;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/9/30 15:38
 */
public class MainFragment extends BaseFragment {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_main;
    }

    @OnClick(R.id.button)
    public void button() {
        startActivity(new Intent(getActivity(), TabInfoActivity.class));
    }
}
