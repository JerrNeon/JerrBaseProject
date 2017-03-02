package com.cw.jerrbase.activity.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.cw.jerrbase.base.activity.BaseTabActivity;
import com.cw.jerrbase.fragment.other.RecyerViewFragment;
import com.cw.jerrbase.fragment.other.RxJavaFragment;
import com.cw.jerrbase.fragment.other.TabInfoFragment;
import com.cw.jerrbase.fragment.other.TabInfoLvFragment;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/8/23 15:28
 */
public class TabInfoActivity extends BaseTabActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOffscreenPageLimit();
    }

    @Override
    protected Fragment[] getFragments() {
        return new Fragment[]{TabInfoLvFragment.newInstance("1", TabInfoLvFragment.class), TabInfoLvFragment.newInstance("2", TabInfoLvFragment.class), RecyerViewFragment.newInstance("3", RecyerViewFragment.class), RxJavaFragment.newInstance("4", RxJavaFragment.class), TabInfoFragment.newInstance("5", TabInfoFragment.class)};
    }

    @Override
    protected String[] getTitles() {
        return new String[]{"tab1", "tab2", "tab3", "tab4", "tab5"};
    }
}
