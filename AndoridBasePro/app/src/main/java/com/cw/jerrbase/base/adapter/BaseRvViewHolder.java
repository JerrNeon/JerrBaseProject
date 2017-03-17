package com.cw.jerrbase.base.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cw.jerrbase.base.glide.GlideUtil;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (RecyclerView的ViewHolder)
 * @create by: chenwei
 * @date 2017/3/16 14:38
 */
public class BaseRvViewHolder extends RecyclerView.ViewHolder {

    protected SparseArray<View> mViews = null;
    protected Context mContext = null;
    protected Fragment mFragment = null;
    protected View mItemView = null;

    public BaseRvViewHolder(@NonNull Context context, @NonNull View itemView) {
        super(itemView);
        this.mContext = context;
        this.mItemView = itemView;
        mViews = new SparseArray<>();
    }

    public BaseRvViewHolder(@NonNull Context context, @NonNull Fragment fragment, @NonNull View itemView) {
        this(context, itemView);
        this.mFragment = fragment;
    }

    public static BaseRvViewHolder create(@NonNull Context context, @NonNull View itemView) {
        return new BaseRvViewHolder(context, itemView);
    }

    public static BaseRvViewHolder create(@NonNull Context context, @NonNull Fragment fragment, @NonNull View itemView) {
        return new BaseRvViewHolder(context, fragment, itemView);
    }

    /**
     * 获取itemView
     *
     * @return
     */
    public View getItemView() {
        return mItemView;
    }

    /**
     * 根据资源Id获取相应的View
     *
     * @param viewId
     * @param <T>
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
        TextView tv = getView(viewId);
        tv.setText(content);
    }

    /**
     * ImageView设置图片
     *
     * @param viewId
     * @param imgResourceId
     */
    public void setImageResource(@IdRes int viewId, @DrawableRes int imgResourceId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(imgResourceId);
    }

    /**
     * ImageView设置图片
     *
     * @param viewId
     * @param drawable
     */
    public void setImageDrawable(@IdRes int viewId, @NonNull Drawable drawable) {
        ImageView iv = getView(viewId);
        iv.setImageDrawable(drawable);
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
