package com.cw.andoridmvp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
public class MainActivity extends BaseTbActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.base_mainBottomView)
    BottomNavigationView mainBottomView;

    /**
     * 再按一次退出程序
     */
    private long exitTime = 0;
    /**
     * 当前菜单项
     */
    private int currPosition;

    private String[] strResource = null;
    private Fragment[] mFragments = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mToolIvLeft.setVisibility(View.GONE);
        strResource = new String[]{"首页", "超低购", "我的"};
        mFragments = new Fragment[]{MainFragment.newInstance(MainFragment.class), MainFragment.newInstance(MainFragment.class), MainFragment.newInstance(MainFragment.class)};
        setDefaultFragment();
        mainBottomView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.base_main_layout;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main:
                currPosition = 0;
                break;
            case R.id.chaodigou:
                currPosition = 1;
                break;
            case R.id.mine:
                currPosition = 2;
                break;
            default:
                currPosition = -1;
                break;
        }
        if (currPosition != -1) {
            changeFragment(currPosition);
            return true;
        }
        return false;
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        if (strResource != null)
            setTitleName(strResource[0]);
        if (mFragments != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.base_mainContent, mFragments[0]).commit();
        }
    }

    private void changeFragment(int position) {
        if (strResource != null)
            setTitleName(strResource[currPosition]);
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
            if (mainBottomView.getMenu().getItem(0).isChecked()) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                mainBottomView.getMenu().getItem(0).setChecked(true);
                mainBottomView.getMenu().getItem(currPosition).setChecked(false);
            }
        } else {
            super.onBackPressed();
            exit();
        }
    }

}
