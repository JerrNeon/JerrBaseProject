package com.cw.jerrbase.fragment.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cw.jerrbase.R;
import com.cw.jerrbase.activity.other.CollapsingToolbarActivity;
import com.cw.jerrbase.adapter.RecyerAdapter;
import com.cw.jerrbase.base.fragment.BaseFragment;
import com.cw.jerrbase.pulltorefresh.PullToRefreshBase;
import com.cw.jerrbase.pulltorefresh.PullToRefreshRecyerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/17 9:39
 */
public class RecyerViewFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener {

    @BindView(R.id.pRecyerView)
    PullToRefreshRecyerView mPrecyclerView;

    private RecyclerView mRecyclerView;
    RecyerAdapter adapter = null;
    private List<String> s = null;

    private int pageIndex = 1, pageSize = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        init();
        initData();
        return mView;
    }

    private void init() {
        mPrecyclerView.setPullRefreshEnabled(true);
        mPrecyclerView.setPullLoadEnabled(true);
        mPrecyclerView.setOnRefreshListener(this, R.id.pRecyerView);
        mRecyclerView = mPrecyclerView.getRefreshableView();

        s = new ArrayList<>();
        adapter = new RecyerAdapter(mContext, s);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyerAdapter.onItemClickListener() {
            @Override
            public void onItemCLick(int position) {
                startActivity(new Intent(mContext, CollapsingToolbarActivity.class));
            }
        });
    }

    private void initData() {
        if (pageIndex == 1 && !s.isEmpty())
            s.clear();
        for (int i = 0; i < pageSize; i++) {
            s.add(i + "");
        }
        adapter.notifyDataSetChanged();
        mPrecyclerView.onPullUpRefreshComplete();
        mPrecyclerView.onPullDownRefreshComplete();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_recyerview;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pageIndex = 1;
        initData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pageIndex++;
        initData();
    }
}
