package com.cw.andoridmvp.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cw.andoridmvp.R;
import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (BaseTbActivity) toolbar不需要自定义
 * @create by: chenwei
 * @date 2016/8/23 11:33
 */
public abstract class BaseTbActivity extends BaseActivity {

    /**
     * 标题栏Toolbar
     */
    protected Toolbar mToolbar;
    /**
     * 将被替换的自定义布局
     */
    protected LinearLayout contentLayout;
    /**
     * 标题栏与自定义布局间的分割线
     */
    protected TextView divider;
    /**
     * title标题名
     */
    protected TextView tv_titleName;
    /**
     * 左边文字
     */
    protected TextView mToolTvLeft;
    /**
     * 左边图标
     */
    protected ImageView mToolIvLeft;
    /**
     * 右边文字
     */
    protected TextView mToolTvRight;
    /**
     * 右边图标
     */
    protected ImageView mToolIvRight;

    /**
     * ButterKnife操作对象
     */
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_layout);
        initView();
        setToolBar(mToolbar);
        setContentViewWithDefaultTitle(getLayoutResourceId());
        unbinder = ButterKnife.bind(this);
        setStatusBar();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.base_toolbar);
        tv_titleName = (TextView) findViewById(R.id.midTitle);
        mToolTvLeft = (TextView) findViewById(R.id.tv_title_left);
        mToolIvLeft = (ImageView) findViewById(R.id.left_iv);
        mToolTvRight = (TextView) findViewById(R.id.tv_title_right);
        mToolIvRight = (ImageView) findViewById(R.id.right_iv);
        divider = (TextView) findViewById(R.id.divider);
        contentLayout = (LinearLayout) findViewById(R.id.base_contentLayout);
    }

    protected abstract int getLayoutResourceId();

    /**
     * 使用默认标题栏
     */
    private void setContentViewWithDefaultTitle(int resourceId) {
        if (resourceId > 0) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(resourceId, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            contentLayout.addView(v, params);
        }
    }

    protected void setStatusBar(){
        StatusBarUtil.setColor(mContext, ContextCompat.getColor(mContext, R.color.colorPrimary), 0);
    }

    /**
     * 设置标题名字
     *
     * @param titleName 标题名字
     */
    protected void setTitleName(String titleName) {
        tv_titleName.setText(titleName);
    }

    /**
     * 设置左边文字
     *
     * @param leftName
     */
    protected void setLeftName(String leftName) {
        mToolTvLeft.setText(leftName);
    }

    /**
     * 设置左边图标
     *
     * @param leftIcon
     */
    protected void setLeftIcon(int leftIcon) {
        mToolIvLeft.setImageResource(leftIcon);
    }

    /**
     * 设置右边文字
     *
     * @param rightName
     */
    protected void setRightName(String rightName) {
        mToolTvRight.setText(rightName);
    }

    /**
     * 设置右边图标
     *
     * @param rightIcon
     */
    protected void setRightIcon(int rightIcon) {
        mToolIvRight.setImageResource(rightIcon);
    }

    /**
     * 标题栏设置
     *
     * @param titleName 标题
     * @param strLeft   左文字
     * @param strRight  右文字
     * @param imgLeft   左图 0:使用默认返回键
     * @param imgRight  右图
     */
    protected void setTitle(String titleName, String strLeft, String strRight, int imgLeft, int imgRight) {
        setTitleName(titleName);
        if (strLeft != null && !"".equals(strLeft))
            setLeftName(strLeft);
        if (strRight != null && !"".equals(strRight))
            setRightName(strRight);
        if (imgLeft != 0)
            setLeftIcon(imgLeft);
        if (imgRight != 0)
            setRightIcon(imgRight);
    }

    /**
     * 标题文字点击
     */
    @OnClick(R.id.midTitle)
    public void onClickMidTitle() {

    }

    /**
     * 左边文字点击
     */
    @OnClick(R.id.tv_title_left)
    public void onClickLeftTitle() {

    }

    /**
     * 左边图标点击
     */
    @OnClick(R.id.left_iv)
    public void onClickLeftImg() {
        this.finish();
    }

    /**
     * 右边文字点击
     */
    @OnClick(R.id.tv_title_right)
    public void onClickRightTitle() {

    }

    /**
     * 右边图标点击
     */
    @OnClick(R.id.right_iv)
    public void onClickRightImg() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
