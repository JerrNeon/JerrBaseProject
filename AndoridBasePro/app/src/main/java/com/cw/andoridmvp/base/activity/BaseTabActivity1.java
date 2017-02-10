package com.cw.andoridmvp.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import com.cw.andoridmvp.R;
import com.cw.andoridmvp.widget.lazyviewpager.LazyFragmentPagerAdapter;
import com.cw.andoridmvp.widget.viewpagerindicator.LazyTabPageIndicator;
import com.cw.andoridmvp.widget.viewpagerindicator.LazyViewPager;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (base选项卡activity--->使用LazyTabPageIndicator和LazyViewPager实现的)
 * @create by: chenwei
 * @date 2016/8/23 15:08
 */
@Deprecated
public abstract class BaseTabActivity1 extends BaseTbActivity {

    protected Fragment[] fragments = null;
    protected String[] titles = null;

    protected LazyTabPageIndicator tabLayout = null;
    protected LazyViewPager tabViewPager = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabView();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.base_tabactivity_layout1;
    }

    private void initTabView() {
        fragments = getFragments();
        titles = getTitles();

        tabLayout = (LazyTabPageIndicator) findViewById(R.id.basetab_layout);
        tabViewPager = (LazyViewPager) findViewById(R.id.basetab_viewpager);
        tabViewPager.setAdapter(new BaseTabFragmentAdapter(getSupportFragmentManager()));
        tabLayout.setViewPager(tabViewPager);
    }


    protected abstract Fragment[] getFragments();

    protected abstract String[] getTitles();

    private class BaseTabFragmentAdapter extends LazyFragmentPagerAdapter {

        public BaseTabFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        protected Fragment getItem(ViewGroup container, int position) {
            return fragments[position];
        }
    }
}
