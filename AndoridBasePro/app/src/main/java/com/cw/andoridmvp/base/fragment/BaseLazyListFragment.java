package com.cw.andoridmvp.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cw.andoridmvp.R;
import com.cw.andoridmvp.base.adapter.BaseListAdapter;
import com.cw.andoridmvp.pulltorefresh.PullToRefreshBase;
import com.cw.andoridmvp.pulltorefresh.PullToRefreshListView;

import java.util.List;

import butterknife.BindView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (base 刷新fragment--->带懒加载, 适合与选项卡配合使用)
 * @create by: chenwei
 * @date 2016/10/8 15:46
 */
public abstract class BaseLazyListFragment<T> extends BaseFragment implements PullToRefreshBase.OnRefreshListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    /**
     * 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，
     * 在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
     */
    protected boolean isPrepared;
    /**
     * 标志当前页面是否可见
     */
    protected boolean isVisible;
    /**
     * 是否已经加载过
     */
    protected boolean isLoad;

    /**
     * 当前页
     */
    protected int pageIndex = 1;
    /**
     * 每页的个数
     */
    protected int pageSize = 10;
    /**
     * ListView
     */
    protected ListView mListView = null;
    /**
     * 适配器
     */
    protected BaseListAdapter mAdapter = null;

    /**
     * 请求类型
     */
    protected enum RequestType {
        PULLTOREFRESHLISTVIEW, LISTVIEW;//带刷新的/不带刷新的
    }

    @BindView(R.id.common_pullrefreshLv)
    PullToRefreshListView mPullToRefreshListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        initLv();
        setLvListener();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //懒加载
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    private void initLv() {
        mAdapter = getAdapter();
        mListView = mPullToRefreshListView.getRefreshableView();
        mPullToRefreshListView.setAdapter(mAdapter);
    }

    private void setLvListener() {
        if (getRequestType() == RequestType.PULLTOREFRESHLISTVIEW) {
            mPullToRefreshListView.setPullRefreshEnabled(true);
            mPullToRefreshListView.setPullLoadEnabled(true);
            mPullToRefreshListView.setOnRefreshListener(this, R.id.common_pullrefreshLv);
        } else if (getRequestType() == RequestType.LISTVIEW) {
            mPullToRefreshListView.setPullRefreshEnabled(false);
            mPullToRefreshListView.setPullLoadEnabled(false);
        }
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.common_pullrefresh_lv;
    }

    protected abstract void sendRequest();

    protected abstract BaseListAdapter getAdapter();

    protected abstract RequestType getRequestType();

    /**
     * 更新数据
     *
     * @param list 数据集
     */
    public void updateRefreshAndData(List<T> list) {
        switch (getRequestType()) {
            case PULLTOREFRESHLISTVIEW:
                if (list.size() < pageSize)
                    mPullToRefreshListView.setPullLoadEnabled(false);
                else
                    mPullToRefreshListView.setPullRefreshEnabled(true);
                if (pageIndex == 1)
                    mAdapter.clear();
                mAdapter.addAll(list);
                mAdapter.notifyDataSetChanged();
                break;
            case LISTVIEW:
                mAdapter.clear();
                mAdapter.addAll(list);
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    /**
     * 设置下拉或上拉完成(当请求完成时)
     */
    public void setPullUpOrDownRefreshComplete() {
        mPullToRefreshListView.onPullUpRefreshComplete();
        mPullToRefreshListView.onPullDownRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pageIndex = 1;
        sendRequest();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pageIndex++;
        sendRequest();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        return false;
    }

    /**
     * 页面可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 页面不可见
     */
    protected void onInvisible() {
    }

    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isVisible || !isPrepared)
            return;
        if (!isLoad) {
            sendRequest();//数据请求
            isLoad = true;
        }
    }

}
