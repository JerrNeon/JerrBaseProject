package com.cw.jerrbase.fragment.other;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.cw.jerrbase.activity.other.CollapsingToolbarActivity;
import com.cw.jerrbase.adapter.RecyerAdapter;
import com.cw.jerrbase.base.adapter.BaseRvAdapter;
import com.cw.jerrbase.base.adapter.BaseRvViewHolder;
import com.cw.jerrbase.base.fragment.BaseRvFragment;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/17 9:39
 */
public class RecyerViewFragment extends BaseRvFragment<String> {

    @Override
    protected void sendRequest() {
        if (pageIndex == 1 && mAdapter.isNotEmpty())
            mAdapter.clear();
        for (int i = 0; i < pageSize; i++) {
            mAdapter.add(object2Str(i));
        }
        mAdapter.notifyDataSetChanged();
        setPullUpOrDownRefreshComplete();
    }

    @Override
    protected BaseRvAdapter<String> getAdapter() {
        return new RecyerAdapter(mContext, mFragment);
    }

    @Override
    protected RequestType getRequestType() {
        return RequestType.PULLTOREFRESH_RECYCLERVIEW;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }

    @Override
    public void onItemCLick(BaseRvViewHolder holder, int position, String bean) {
        super.onItemCLick(holder, position, bean);
        logI("onItemCLick: " + bean);
        startActivity(new Intent(mContext, CollapsingToolbarActivity.class));
    }

    @Override
    public void onItemLongClick(BaseRvViewHolder holder, int position, String bean) {
        super.onItemLongClick(holder, position, bean);
        logI("onItemLongClick: " + bean);
    }
}
