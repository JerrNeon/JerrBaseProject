package com.cw.jerrbase.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.cw.jerrbase.R;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (base选项卡activity--->使用TabLayout和ViewPager实现的)
 * @create by: chenwei
 * @date 2016/8/23 15:08
 */
public abstract class BaseTabActivity extends BaseTbActivity {

    protected Fragment[] fragments = null;
    protected String[] titles = null;

    protected TabLayout tabLayout = null;
    protected ViewPager tabViewPager = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabView();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.base_tabactivity_layout;
    }

    private void initTabView() {
        fragments = getFragments();
        titles = getTitles();

        tabLayout = (TabLayout) findViewById(R.id.basetab_layout);
        tabViewPager = (ViewPager) findViewById(R.id.basetab_viewpager);
        tabViewPager.setAdapter(new BaseTabFragmentAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(tabViewPager);
    }

    protected abstract Fragment[] getFragments();

    protected abstract String[] getTitles();

    /**
     * 设置ViewPager的预加载数量(没有使用BaseLazyFragment或BaseLazyListFragment时无需调用此方法)
     * 注：设置了预加载为总Tab的个数后，每次点击Tab时就不会再去重新请求数据(还没找到更好的方法解决真正的懒加载问题)
     */
    protected void setOffscreenPageLimit() {
        if (fragments != null)
            tabViewPager.setOffscreenPageLimit(fragments.length);
    }

    private class BaseTabFragmentAdapter extends FragmentPagerAdapter {

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
        public Fragment getItem(int position) {
            return fragments[position];
        }

    }
}
