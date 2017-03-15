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
        return new Fragment[]{TabInfoLvFragment.newInstance(TabInfoLvFragment.class,"1"), TabInfoLvFragment.newInstance(TabInfoLvFragment.class,"2"), RecyerViewFragment.newInstance(RecyerViewFragment.class,"3"), RxJavaFragment.newInstance(RxJavaFragment.class,"4"), TabInfoFragment.newInstance(TabInfoFragment.class,"5")};
    }

    @Override
    protected String[] getTitles() {
        return new String[]{"tab1", "tab2", "tab3", "tab4", "tab5"};
    }
}
