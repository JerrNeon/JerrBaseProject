package com.cw.andoridmvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cw.andoridmvp.R;
import com.cw.andoridmvp.base.activity.BaseTbActivity;
import com.cw.andoridmvp.fragment.MainFragment;

import butterknife.BindView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (首页)
 * @create by: chenwei
 * @date 2016/8/23 17:11
 */
public class MainActivity extends BaseTbActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.base_mainBottomBar)
    BottomNavigationBar mainBottomBar;

    /**
     * 再按一次退出程序
     */
    private long exitTime = 0;

    private int[] imgResource = null;
    private String[] strResource = null;
    private Fragment[] mFragments = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mToolIvLeft.setVisibility(View.GONE);
        imgResource = new int[]{R.drawable.ic_directions_car_white_24dp, R.drawable.ic_directions_car_white_24dp, R.drawable.ic_directions_car_white_24dp};
        strResource = new String[]{"首页", "超低购", "我的"};
        mFragments = new Fragment[]{MainFragment.instance(), MainFragment.instance(), MainFragment.instance()};
        for (int i = 0; i < imgResource.length; i++) {
            mainBottomBar.addItem(new BottomNavigationItem(imgResource[i], strResource[i]));
        }
        mainBottomBar.initialise();
        mainBottomBar.setTabSelectedListener(this);
        setDefaultFragment();
    }

    @Override
    public void onTabSelected(int position) {
        setTitleName(strResource[position]);
        changeFragment(position);
    }

    @Override
    public void onTabUnselected(int position) {
        if (mFragments != null && position < mFragments.length) {
            getSupportFragmentManager().beginTransaction().remove(mFragments[position]).commitAllowingStateLoss();
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.base_main_layout;
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        setTitleName(strResource[0]);
        if (mFragments != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.base_mainContent, mFragments[0]).commit();
        }
    }

    private void changeFragment(int position) {
        if (mFragments != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = mFragments[position];
            if (fragment.isAdded()) {
                ft.replace(R.id.base_mainContent, fragment);
            } else {
                ft.add(R.id.base_mainContent, fragment);
            }
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 按两次退出
     */
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            if (mainBottomBar.getCurrentSelectedPosition() == 0) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else
                mainBottomBar.selectTab(0);
        } else {
            super.onBackPressed();
            exit();
        }
    }

}
