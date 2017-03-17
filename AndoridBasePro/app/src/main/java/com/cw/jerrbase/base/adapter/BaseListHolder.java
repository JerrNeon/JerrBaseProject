package com.cw.jerrbase.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cw.jerrbase.base.glide.GlideUtil;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (ListView的ViewHolder)
 * @create by: chenwei
 * @date 2017/3/16 14:38
 */
public class BaseListHolder {

    private SparseArray<View> mViews = null;
    private Context mContext = null;
    private Fragment mFragment = null;
    private View mItemView = null;

    private BaseListHolder(@NonNull Context context, @LayoutRes int layoutId, ViewGroup parent) {
        this.mContext = context;
        this.mViews = new SparseArray<>();
        this.mItemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mItemView.setTag(this);
    }

    private BaseListHolder(@NonNull Context context, @NonNull Fragment fragment, @LayoutRes int layoutId, ViewGroup parent) {
        this(context, layoutId, parent);
        this.mFragment = fragment;
    }

    public View getItemView() {
        return mItemView;
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param convertView
     * @param parent      ViewGroup
     * @param layoutId    layoutID
     * @return
     */
    public static BaseListHolder create(Context mContext, View convertView, ViewGroup parent, @LayoutRes int layoutId) {
        if (convertView == null)
            return new BaseListHolder(mContext, layoutId, parent);
        return (BaseListHolder) convertView.getTag();
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param convertView
     * @param parent      ViewGroup
     * @param layoutId    layoutID
     * @return
     */
    public static BaseListHolder create(Context mContext, Fragment fragment, View convertView, ViewGroup parent, @LayoutRes int layoutId) {
        if (convertView == null)
            return new BaseListHolder(mContext, fragment, layoutId, parent);
        return (BaseListHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mItemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置View可见或不可见
     *
     * @param viewId
     * @param visibility View.VISIBLE/View.GONE/View.INVISIBLE
     */
    public void setVisibity(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
    }

    /**
     * TextView设置文字
     *
     * @param viewId
     * @param content
     */
    public void setText(@IdRes int viewId, @NonNull String content) {
        TextView view = getView(viewId);
        view.setText(content);
    }

    /**
     * ImageView 设置资源图片
     *
     * @param viewId
     * @param imgResId
     */
    public void setImageResource(@IdRes int viewId, @DrawableRes int imgResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imgResId);
    }

    /**
     * ImageView drawable
     *
     * @param viewId
     * @param img
     */
    public void setImageDrawable(@IdRes int viewId, @NonNull Drawable img) {
        ImageView view = getView(viewId);
        view.setImageDrawable(img);
    }

    /**
     * ImageView 加载网络图片
     *
     * @param viewId
     * @param imgUrl
     */
    public void displayImage(@IdRes int viewId, @Nullable String imgUrl) {
        displayImage(viewId, imgUrl, -1);
    }

    /**
     * ImageView 加载网络图片
     *
     * @param viewId
     * @param imgUrl
     * @param errorImgResourceId 加载失败或加载错误的图片
     */
    public void displayImage(@IdRes int viewId, @Nullable String imgUrl, @IdRes int errorImgResourceId) {
        ImageView view = getView(viewId);
        if (mContext instanceof Activity) {
            if (errorImgResourceId == -1)
                GlideUtil.displayImage((Activity) mContext, imgUrl, view);
            else
                GlideUtil.displayImage((Activity) mContext, imgUrl, view, errorImgResourceId);
        } else if (mContext instanceof FragmentActivity) {
            if (errorImgResourceId == -1)
                GlideUtil.displayImage(mFragment, imgUrl, view);
            else
                GlideUtil.displayImage(mFragment, imgUrl, view, errorImgResourceId);
        } else {
            if (errorImgResourceId == -1)
                GlideUtil.displayImage(mContext, imgUrl, view);
            else
                GlideUtil.displayImage(mContext, imgUrl, view, errorImgResourceId);
        }
    }

    /**
     * ImageView 加载网络图片(圆形)
     *
     * @param viewId
     * @param imgUrl
     */
    public void displayImageWithRound(@IdRes int viewId, @Nullable String imgUrl) {
        displayImageWithRound(viewId, imgUrl, -1);
    }

    /**
     * ImageView 加载网络图片(圆形)
     *
     * @param viewId
     * @param imgUrl
     * @param errorImgResourceId 加载失败或加载错误的图片
     */
    public void displayImageWithRound(@IdRes int viewId, @Nullable String imgUrl, @IdRes int errorImgResourceId) {
        ImageView view = getView(viewId);
        if (mContext instanceof Activity) {
            if (errorImgResourceId == -1)
                GlideUtil.displayImageWithRound((Activity) mContext, imgUrl, view);
            else
                GlideUtil.displayImageWithRound((Activity) mContext, imgUrl, view, errorImgResourceId);
        } else if (mContext instanceof FragmentActivity) {
            if (errorImgResourceId == -1)
                GlideUtil.displayImageWithRound(mFragment, imgUrl, view);
            else
                GlideUtil.displayImageWithRound(mFragment, imgUrl, view, errorImgResourceId);
        } else {
            if (errorImgResourceId == -1)
                GlideUtil.displayImageWithRound(mContext, imgUrl, view);
            else
                GlideUtil.displayImageWithRound(mContext, imgUrl, view, errorImgResourceId);
        }
    }

}
