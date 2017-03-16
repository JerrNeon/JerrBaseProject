package com.cw.jerrbase.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.adapter.BaseRvAdapter;
import com.cw.jerrbase.base.adapter.BaseRvViewHolder;
import com.cw.jerrbase.pulltorefresh.PullToRefreshBase;
import com.cw.jerrbase.pulltorefresh.PullToRefreshRecyerView;

import java.util.List;

import butterknife.BindView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (base 刷新RecyclerView Fragment--->带懒加载)
 * @create by: chenwei
 * @date 2017/3/16 17:26
 */
public abstract class BaseLazyRvFragment<T> extends BaseLazyFragment implements PullToRefreshBase.OnRefreshListener, BaseRvAdapter.onItemClickListener<T>, BaseRvAdapter.onItemLongClickListener<T> {

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
    protected RecyclerView mRecyclerView = null;
    /**
     * 适配器
     */
    protected BaseRvAdapter<T> mAdapter = null;

    /**
     * 请求类型
     */
    protected enum RequestType {
        RECYCLERVIEW, PULLTOREFRESH_RECYCLERVIEW;//带刷新的/不带刷新的
    }

    @BindView(R.id.common_pullrefreshRv)
    PullToRefreshRecyerView mPullToRefreshRecyerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        initLv();
        setLvListener();
        return mView;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.common_pullrefresh_rv;
    }

    private void initLv() {
        mAdapter = getAdapter();
        mRecyclerView = mPullToRefreshRecyerView.getRefreshableView();
        mRecyclerView.setLayoutManager(getLayoutManager() == null ? new LinearLayoutManager(mContext) : getLayoutManager());
        mPullToRefreshRecyerView.setAdapter(mAdapter);
    }

    private void setLvListener() {
        if (getRequestType() == RequestType.PULLTOREFRESH_RECYCLERVIEW) {
            mPullToRefreshRecyerView.setPullRefreshEnabled(true);
            mPullToRefreshRecyerView.setPullLoadEnabled(true);
            mPullToRefreshRecyerView.setOnRefreshListener(this, R.id.common_pullrefreshLv);
        } else if (getRequestType() == RequestType.RECYCLERVIEW) {
            mPullToRefreshRecyerView.setPullRefreshEnabled(false);
            mPullToRefreshRecyerView.setPullLoadEnabled(false);
        }
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
    }

    protected abstract void sendRequest();

    protected abstract BaseRvAdapter<T> getAdapter();

    protected abstract RequestType getRequestType();

    /**
     * 返回RecyclerView的LayoutManager
     * 为空时默认为LinearLayoutManager
     *
     * @return LinearLayoutManager/GridLayoutManager/StaggeredGridLayoutManager
     */
    protected abstract RecyclerView.LayoutManager getLayoutManager();

    /**
     * 刷新数据
     *
     * @param list 数据集
     */
    public void updateRefreshAndData(List<T> list) {
        switch (getRequestType()) {
            case PULLTOREFRESH_RECYCLERVIEW:
                if (list.size() < pageSize)
                    mPullToRefreshRecyerView.setPullLoadEnabled(false);
                else
                    mPullToRefreshRecyerView.setPullRefreshEnabled(true);
                if (pageIndex == 1)
                    mAdapter.clear();
                mAdapter.addAll(list);
                mAdapter.notifyDataSetChanged();
                break;
            case RECYCLERVIEW:
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
        mPullToRefreshRecyerView.onPullUpRefreshComplete();
        mPullToRefreshRecyerView.onPullDownRefreshComplete();
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
    public void onItemCLick(BaseRvViewHolder holder, int position, T bean) {

    }

    @Override
    public void onItemLongClick(BaseRvViewHolder holder, int position, T bean) {

    }

    @Override
    protected void lazyLoad() {
        if (!isVisible || !isPrepared)
            return;
        if (!isLoad) {
            sendRequest();//数据请求
            isLoad = true;
        }
    }
}
