package com.cw.andoridmvp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cw.andoridmvp.R;
import com.cw.andoridmvp.activity.CollapsingToolbarActivity;
import com.cw.andoridmvp.adapter.recyerAdapter;
import com.cw.andoridmvp.base.fragment.BaseFragment;

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
public class RecyerViewFragment extends BaseFragment {

    @BindView(R.id.recyerview)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        init();
        return mView;
    }

    private void init() {
        List<String> s = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            s.add(i + "");
        }
        recyerAdapter adapter = new recyerAdapter(mContext, s);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new recyerAdapter.onItemClickListener() {
            @Override
            public void onItemCLick(int position) {
                startActivity(new Intent(mContext, CollapsingToolbarActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_recyerview;
    }

}
