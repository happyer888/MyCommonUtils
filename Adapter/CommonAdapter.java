package com.keithly.demo.testdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 万能适配器
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 数据源
     */
    protected List<T> mList;
    /**
     * Item布局ID
     */
    protected int layoutId;

    public CommonAdapter(Context context, List<T> list, int layoutId) {
        this.mContext = context;
        this.mList = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getViewHolder(mContext, convertView, parent, layoutId);
        fillData(holder, position);
        return holder.getMConvertView();
    }

    /**
     * 填充数据的抽象方法
     */
    protected abstract void fillData(ViewHolder holder, int position);
}