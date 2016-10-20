package com.cw.andoridmvp.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.cw.andoridmvp.R;
import com.cw.andoridmvp.base.activity.BaseTbActivity;
import com.cw.andoridmvp.fragment.MainFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (首页)
 * @create by: chenwei
 * @date 2016/8/23 17:11
 */
public class MainActivity extends BaseTbActivity implements OnTabSelectListener {

    @BindView(R.id.base_mainBottomBar)
    BottomBar mainBottomBar;

    /**
     * 再按一次退出程序
     */
    private long exitTime = 0;

    private Fragment[] mFragments = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mToolIvLeft.setVisibility(View.GONE);
        mFragments = new Fragment[]{MainFragment.instance(), MainFragment.instance(), MainFragment.instance()};
        mainBottomBar.setOnTabSelectListener(this);
        mainBottomBar.selectTabWithId(R.id.tab_1);
    }

    @Override
    public void onTabSelected(@IdRes int tabId) {
        if (mFragments != null)
            switch (tabId) {
                case R.id.tab_1:
                    setTitleName("首页");
                    changeFragment(0);
                    break;
                case R.id.tab_2:
                    setTitleName("超低购");
                    changeFragment(1);
                    break;
                case R.id.tab_3:
                    setTitleName("我的");
                    changeFragment(2);
                    break;
            }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.base_main_layout;
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
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            exit();
        }
    }

}
