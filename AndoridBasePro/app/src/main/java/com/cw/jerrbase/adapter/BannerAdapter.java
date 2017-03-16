package com.cw.jerrbase.adapter;

import android.content.Context;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.adapter.BaseListAdapter;
import com.cw.jerrbase.base.adapter.BaseListHolder;

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
    public int getLayoutResourceId() {
        return R.layout.item_main;
    }

    @Override
    public void getView(int position, BaseListHolder holder, String bean) {
        holder.setText(R.id.tv_banner_transform, bean);
    }
}
