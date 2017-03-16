package com.cw.jerrbase.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.adapter.BaseListAdapter;
import com.cw.jerrbase.pulltorefresh.PullToRefreshBase;
import com.cw.jerrbase.pulltorefresh.PullToRefreshListView;

import java.util.List;

import butterknife.BindView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (base 刷新fragment--->带懒加载, 适合与选项卡配合使用)
 * @create by: chenwei
 * @date 2016/10/8 15:46
 */
public abstract class BaseLazyListFragment<T> extends BaseLazyFragment implements PullToRefreshBase.OnRefreshListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

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
    protected BaseListAdapter<T> mAdapter = null;

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
        onItemClick(adapterView, view, position, id, mAdapter.getItem((int) id));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        return onItemLongClick(adapterView, view, position, id, mAdapter.getItem((int) id));
    }

    public abstract void onItemClick(AdapterView<?> adapterView, View view, int position, long id, T bean);

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id, T bean) {
        return false;
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
