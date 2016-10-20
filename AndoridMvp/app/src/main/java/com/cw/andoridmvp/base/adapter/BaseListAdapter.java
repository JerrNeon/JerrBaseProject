package com.cw.andoridmvp.base.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by guanzhe on 14-9-23.
 * modify by tulong on 2015/12/18
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> mList;// 列表List
    protected Context mContext;
    protected LayoutInflater inflate;

    /**
     * 构造器
     *
     * @param list
     *            起始数据
     */
    public BaseListAdapter(List<T> list) {
        mList = list;
    }

    public BaseListAdapter(Context context) {
        mContext = context;
    }

    public BaseListAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
        //this.layoutId = layoutId;
        inflate = LayoutInflater.from(context);
    }

    /**
     * 获取item布局id
     * @return
     */
    public abstract int getLayoutId();
    @Override
    public int getCount() {
        return (mList == null) ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return (mList == null) ? null : mList.get(position);
    }


    public T getLastItme() {
        return (mList == null) ? null : mList.get(mList.size() - 1);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    //重写这里修改getview
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToolViewHolder holder = ToolViewHolder.get(mContext,convertView, parent, getLayoutId());
        //getView(position, holder, mList.get(position));
        getView(position, holder, position >= mList.size() ? null : mList.get(position));
        return holder.getConvertView();
    }
    public abstract void getView(int position, ToolViewHolder holder, T bean);

    /**
     * 获取该适配器的列表数据
     *
     * @return
     */
    public List<T> getData() {
        return mList;
    }

    public void add(T bean) {
        if (bean!= null) {
            mList.add(bean);
        }
    }

    public void addAll(List<T> beans) {
        if (beans != null) {
            mList.addAll(beans);
        }
    }

    public T remove(int position) {
        if (position >= 0 && position < mList.size()) {
            return mList.remove(position);
        }
        return null;
    }

    public void clean() {
        mList.clear();
    }

    public String getNotEmptyData(String str, String defVal) {
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        if (!TextUtils.isEmpty(defVal)) {
            return defVal;
        }
        return "";
    }

    public String getNotEmptyData(String str) {
        return getNotEmptyData(str, "");
    }
}



