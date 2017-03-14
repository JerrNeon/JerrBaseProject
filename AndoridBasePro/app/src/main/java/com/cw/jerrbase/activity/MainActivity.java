package com.cw.jerrbase.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.activity.BaseActivity;
import com.cw.jerrbase.fragment.ComponentFragment;
import com.cw.jerrbase.fragment.MainFragment;
import com.cw.jerrbase.fragment.MineFragment;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (首页)
 * @create by: chenwei
 * @date 2016/8/23 17:11
 */
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.base_mainBottomView)
    BottomNavigationView mainBottomView;
    @BindView(R.id.main_drawerLayout)
    DrawerLayout mMainDrawerLayout;
    @BindView(R.id.left_iv)
    ImageView mLeftIv;
    @BindView(R.id.tv_title_left)
    TextView mTvTitleLeft;
    @BindView(R.id.tv_title_right)
    TextView mTvTitleRight;
    @BindView(R.id.right_iv)
    ImageView mRightIv;
    @BindView(R.id.midTitle)
    TextView mMidTitle;

    /**
     * 再按一次退出程序
     */
    private long exitTime = 0;
    /**
     * 当前菜单项
     */
    private int currPosition;
    /**
     * 上一个菜单项
     */
    private int prePositon = 0;

    private String[] strResource = null;
    private Fragment[] mFragments = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_main_layout);
        initButterKnife();
        init();
        initDrawerLayout();
        setStatusBar();
    }

    private void init() {
        mLeftIv.setImageResource(R.drawable.ic_menu_slide);
        strResource = new String[]{"Home", "Component", "Mine"};
        mFragments = new Fragment[]{MainFragment.newInstance(MainFragment.class), ComponentFragment.newInstance(ComponentFragment.class), MineFragment.newInstance(MineFragment.class)};
        setDefaultFragment();
        mainBottomView.setOnNavigationItemSelectedListener(this);

        mLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMainDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void initDrawerLayout() {
        mMainDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mMainDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float leftScale = 1 - 0.3f * scale;
                float rightScale = 0.8f + scale * 0.2f;

                //mMenu.setScaleX(leftScale);
                //mMenu.setScaleY(leftScale);
                mMenu.setAlpha(0.6f + 0.4f * (1 - scale));

                mContent.setTranslationX(mMenu.getMeasuredWidth() * (1 - scale));
                mContent.setPivotX(0);
                mContent.setPivotY(mContent.getMeasuredHeight()/2);
                mContent.invalidate();
                mContent.setScaleX(rightScale);
                mContent.setScaleY(rightScale);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void setStatusBar() {
        super.setStatusBar();
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(mActivity, mMainDrawerLayout, ContextCompat.getColor(mContext, R.color.colorPrimary));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main:
                currPosition = 0;
                mLeftIv.setVisibility(View.VISIBLE);
                break;
            case R.id.chaodigou:
                currPosition = 1;
                mLeftIv.setVisibility(View.GONE);
                break;
            case R.id.mine:
                currPosition = 2;
                mLeftIv.setVisibility(View.GONE);
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
        if (prePositon == position) return;
        if (strResource != null)
            setTitleName(strResource[currPosition]);
        if (mFragments != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = mFragments[position];
            if (fragment.isAdded()) {
                ft.show(fragment).hide(mFragments[prePositon]);
            } else {
                ft.add(R.id.base_mainContent, fragment);
            }
            ft.commitAllowingStateLoss();
            prePositon = currPosition;
        }
    }

    public void setTitleName(String titleName) {
        mMidTitle.setText(titleName);
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
