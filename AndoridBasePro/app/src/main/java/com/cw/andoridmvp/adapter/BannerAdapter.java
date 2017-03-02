package com.cw.andoridmvp.adapter;

import android.content.Context;

import com.cw.andoridmvp.R;
import com.cw.andoridmvp.base.adapter.BaseListAdapter;
import com.cw.andoridmvp.base.adapter.ToolViewHolder;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (Banner动画测试)
 * @create by: chenwei
 * @date 2017/3/1 17:38
 */
public class BannerAdapter extends BaseListAdapter<String> {

    public BannerAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_main;
    }

    @Override
    public void getView(int position, ToolViewHolder holder, String bean) {
        holder.tvSetText(R.id.tv_banner_transform, bean);
    }
}
