package com.cw.jerrbase.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.cw.jerrbase.R;
import com.cw.jerrbase.base.adapter.BaseListAdapter;
import com.cw.jerrbase.base.adapter.ToolViewHolder;
import com.cw.jerrbase.base.dialog.BaseDialog;
import com.cw.jerrbase.bean.ShareVO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;


/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (分享对话框)
 * @create by: chenwei
 * @date 2017/3/7 18:29
 */
public class ShareDialog extends BaseDialog {

    @BindView(R.id.gv_shareList)
    GridView mGvShare;

    private onItemClickListener mOnItemClickListener = null;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.dialog_share;
    }

    @Override
    protected int getAnimationStyle() {
        return R.style.bottom_in_out;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = super.onCreateView(inflater, container, savedInstanceState);
        init();
        ButterKnife.bind(this, mView);
        return mView;
    }

    private void init() {
        ShareAdapter adapter = new ShareAdapter(mContext);
        adapter.add(new ShareVO(R.drawable.umeng_socialize_wxcircle, "微信朋友圈"));
        adapter.add(new ShareVO(R.drawable.umeng_socialize_wechat, "微信好友"));
        adapter.add(new ShareVO(R.drawable.umeng_socialize_qq, "手机QQ"));
        adapter.add(new ShareVO(R.drawable.umeng_socialize_qzone, "QQ空间"));
        adapter.add(new ShareVO(R.drawable.umeng_socialize_sina, "新浪微博"));
        mGvShare.setAdapter(adapter);
    }

    /**
     * 显示对话框
     *
     * @param manager  FragmentManager
     * @param tag      tag标识
     * @param listener 监听器
     */
    public void show(FragmentManager manager, String tag, onItemClickListener listener) {
        this.show(manager, tag);
        mOnItemClickListener = listener;
    }

    @OnItemClick(R.id.gv_shareList)
    public void onRvItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (mOnItemClickListener != null)
            mOnItemClickListener.onItemClick(position);
        this.dismiss();
    }

    @OnClick(R.id.tv_shareCancel)
    public void onCancelClick() {
        this.dismiss();
    }

    private class ShareAdapter extends BaseListAdapter<ShareVO> {

        public ShareAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayoutId() {
            return R.layout.dialog_item_share;
        }

        @Override
        public void getView(int position, ToolViewHolder holder, ShareVO bean) {
            holder.ivSetImage(R.id.share_img, bean.getImg());
            holder.tvSetText(R.id.share_title, bean.getTitle());
        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }
}
