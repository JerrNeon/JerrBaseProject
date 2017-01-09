package com.cw.andoridmvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cw.andoridmvp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (这里用一句话描述这个类的作用)
 * @create by: chenwei
 * @date 2016/10/17 9:43
 */
public class RecyerAdapter extends RecyclerView.Adapter<RecyerAdapter.itemViewHolder> {

    private Context mContext;
    private List<String> s = new ArrayList<>();
    private onItemClickListener mOnItemClickListener;

    public RecyerAdapter(Context context, List<String> s) {
        mContext = context;
        this.s = s;
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public itemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new itemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fragment_recyerview_item, parent, false));
    }

    @Override
    public void onBindViewHolder(itemViewHolder holder, final int position) {
        holder.mTextView.setText(s.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemCLick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return s == null ? 0 : s.size();
    }

    static class itemViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public itemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.recyerview_tv);
        }
    }

    public interface onItemClickListener {
        void onItemCLick(int position);
    }
}
