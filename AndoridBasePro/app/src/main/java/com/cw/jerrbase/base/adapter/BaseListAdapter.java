package com.cw.jerrbase.base.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cw.jerrbase.base.api.ILog;
import com.cw.jerrbase.base.api.IRoute;
import com.cw.jerrbase.base.api.IToast;
import com.cw.jerrbase.base.api.IUtil;
import com.cw.jerrbase.util.DateUtils;
import com.cw.jerrbase.util.LogUtils;
import com.cw.jerrbase.util.NumberUtils;
import com.cw.jerrbase.util.QMUtil;
import com.cw.jerrbase.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @ClassName: ${CLASS_NAME}
 * @Description: (ListView的适配器)
 * @create by: chenwei
 * @date 2017/3/16 10:35
 */
public abstract class BaseListAdapter<T> extends BaseAdapter implements IRoute, ILog, IToast, IUtil {

    /**
     * 数据集
     */
    protected List<T> mList = null;
    protected Context mContext = null;
    protected Fragment mFragment = null;
    protected LayoutInflater inflate = null;

    public BaseListAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
        inflate = LayoutInflater.from(context);
    }

    public BaseListAdapter(Context context, Fragment fragment) {
        this(context);
        mFragment = fragment;
    }

    /**
     * 获取item布局id
     *
     * @return
     */
    protected abstract int getLayoutResourceId();

    public abstract void getView(int position, BaseListHolder holder, T bean);

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
        BaseListHolder holder = BaseListHolder.create(mContext, mFragment, convertView, parent, getLayoutResourceId());
        getView(position, holder, position >= mList.size() ? null : mList.get(position));
        return holder.getItemView();
    }

    /**
     * 获取该适配器的列表数据
     *
     * @return
     */
    public List<T> getData() {
        return mList;
    }

    public void add(T bean) {
        if (bean != null) {
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

    public void clear() {
        if (!mList.isEmpty() && mList.size() > 0)
            mList.clear();
    }

    public boolean isEmpty() {
        return mList.isEmpty();
    }

    public boolean isNotEmpty() {
        return !mList.isEmpty();
    }

    @Override
    public String getClassName() {
        return getClass().getSimpleName();
    }

    @Override
    public void openActivity(@NonNull Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        mContext.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull int flag) {
        Intent intent = new Intent(mContext, cls);
        intent.setFlags(flag);
        mContext.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull long param) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtra(cls.getSimpleName(), param);
        mContext.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull String param) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtra(cls.getSimpleName(), param);
        mContext.startActivity(intent);
    }

    @Override
    public void openActivity(@NonNull Class<?> cls, @NonNull Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void logI(String message) {
        LogUtils.i(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logW(String message) {
        LogUtils.w(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void logE(String message) {
        LogUtils.e(String.format(messageFormat, getClassName(), message));
    }

    @Override
    public void showToast(String message) {
        ToastUtil.showToast(mContext, message);
    }

    @Override
    public boolean checkObject(Object object) {
        return QMUtil.isEmpty(object);
    }

    @Override
    public String checkStr(String str) {
        return QMUtil.checkStr(str);
    }

    @Override
    public int str2Int(String str) {
        return QMUtil.strToInt(str);
    }

    @Override
    public long str2Long(String str) {
        return QMUtil.strToLong(str);
    }

    @Override
    public float str2Float(String str) {
        return QMUtil.strToFloat(str);
    }

    @Override
    public double str2Double(String str) {
        return QMUtil.strToDouble(str);
    }

    @Override
    public String object2Str(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double)
            return String.valueOf(object);
        return "";
    }

    @Override
    public String formatPrice(String price) {
        return NumberUtils.formatDouble2String(str2Double(price));
    }

    @Override
    public String formatTime(long time) {
        return DateUtils.formateDateLongToString(time);
    }
}



