package com.cw.andoridmvp.activity;

import android.support.v4.app.Fragment;

import com.cw.andoridmvp.base.activity.BaseTabActivity;
import com.cw.andoridmvp.fragment.RecyerViewFragment;
import com.cw.andoridmvp.fragment.RxJavaFragment;
import com.cw.andoridmvp.fragment.TabInfoFragment;
import com.cw.andoridmvp.fragment.TabInfoLvFragment;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/8/23 15:28
 */
public class TabInfoActivity extends BaseTabActivity {

    @Override
    protected Fragment[] getFragments() {
        return new Fragment[]{TabInfoLvFragment.newInstance("1", TabInfoLvFragment.class), TabInfoLvFragment.newInstance("2", TabInfoLvFragment.class), RecyerViewFragment.newInstance("3", RecyerViewFragment.class), RxJavaFragment.newInstance("4", RxJavaFragment.class), TabInfoFragment.newInstance("5", TabInfoFragment.class)};
    }

    @Override
    protected String[] getTitles() {
        return new String[]{"tab1", "tab2", "tab3", "tab4", "tab5"};
    }
}
