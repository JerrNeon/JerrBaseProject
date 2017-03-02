package com.cw.jerrbase.base.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * adapter 持有者
 * @create tu
 * @since 2015/12/18
 */
public class ToolViewHolder {
	private SparseArray<View> mViews;
	private View mConvertView;

	private ToolViewHolder(Context mContext, int layoutId, ViewGroup parent) {
		this.mViews = new SparseArray<View>();
		this.mConvertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
		this.mConvertView.setTag(this);
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param convertView
	 * @param parent
	 *            ViewGroup
	 * @param layoutId
	 *            layoutID
	 * @return
	 */
	public static ToolViewHolder get(Context mContext, View convertView, ViewGroup parent, int layoutId) {
		if (convertView == null)
			return new ToolViewHolder(mContext,layoutId, parent);
		return (ToolViewHolder) convertView.getTag();
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getChildView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	/**
	 * textview设置文字
	 * @param viewId
	 * @param content
	 */
	public void tvSetText(int viewId,String content){
		TextView view = (TextView) mViews.get(viewId);
		if (view == null) {
			view = (TextView) mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		view.setText(content);
	}

	/**
	 * iamgeview设置 drawable
	 * @param viewId
	 * @param img
	 */
	public void ivSetImage(int viewId,Drawable img){
		ImageView view = (ImageView) mViews.get(viewId);
		if (view == null) {
			view = (ImageView) mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		view.setImageDrawable(img);
	}

	/**
	 * imageview 加载网络图片
	 * @param viewId
	 * @param imgUrl
	 */
	@Deprecated
	public void ivSetImage(int viewId,String imgUrl){
		ImageView view = (ImageView) mViews.get(viewId);
		if (view == null) {
			view = (ImageView) mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		//ImageLoadUtil.imageLoader.displayImage(imgUrl, view);
	}

	/**
	 * imageview 设置资源图片
	 * @param viewId
	 * @param imgResId
	 */
	public void ivSetImage(int viewId,int imgResId){
		ImageView view = (ImageView) mViews.get(viewId);
		if (view == null) {
			view = (ImageView) mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		view.setImageResource(imgResId);
	}
}
