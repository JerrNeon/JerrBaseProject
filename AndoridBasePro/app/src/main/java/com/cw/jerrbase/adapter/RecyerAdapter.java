package com.cw.jerrbase.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.adapter.BaseRvAdapter;
import com.cw.jerrbase.base.adapter.BaseRvViewHolder;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/17 9:43
 */
public class RecyerAdapter extends BaseRvAdapter<String> {

    public RecyerAdapter(@NonNull Context context) {
        super(context);
    }

    public RecyerAdapter(@NonNull Context context, @NonNull Fragment fragment) {
        super(context, fragment);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_recyerview_item;
    }

    @Override
    protected void onBindViewHolder(BaseRvViewHolder holder, int position, String bean) {
        holder.setText(R.id.recyerview_tv, bean);
    }

}
