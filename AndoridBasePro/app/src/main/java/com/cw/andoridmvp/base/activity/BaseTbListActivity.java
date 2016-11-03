package com.cw.andoridmvp.base.activity;

import android.os.Bundle;
import android.view.View;
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
 * @Description: (BaseTbListActivity toolbar不需要自定义的带刷新的activity)
 * @create by: chenwei
 * @date 2016/10/8 16:28
 */
public abstract class BaseTbListActivity<T> extends BaseTbActivity implements PullToRefreshBase.OnRefreshListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    /**
     * 当前页
     */
    protected int pageIndex = 1;
    /**
     * 每页的个数
     */
    protected int pageSize = 10;
    /**
     * 是否可以加载
     */
    protected boolean isLoad = false;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLv();
        setLvListener();
        sendRequest();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.common_pullrefresh_lv;
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

    protected abstract void sendRequest();

    protected abstract BaseListAdapter<T> getAdapter();

    protected abstract RequestType getRequestType();

    /**
     * 刷新数据
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
                if (!isLoad)
                    mAdapter.clear();
                mAdapter.addAll(list);
                mAdapter.notifyDataSetChanged();
                mPullToRefreshListView.onPullUpRefreshComplete();
                mPullToRefreshListView.onPullDownRefreshComplete();
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

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pageIndex = 1;
        isLoad = false;
        sendRequest();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pageIndex++;
        isLoad = true;
        sendRequest();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        return false;
    }
}
