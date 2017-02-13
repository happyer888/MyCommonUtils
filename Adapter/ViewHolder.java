package com.keithly.demo.testdemo.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Holder的通用化处理
 */
public class ViewHolder {
    /**
     * View容器，用于存放Holder中的View,相当于一个Map<Integer,View>
     */
    private SparseArray<View> mViews;
    /**
     * Item布局View ConvertView
     */
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId) {
        mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, null);
        mConvertView.setTag(this);
    }

    /**
     * 获取ViewHolder
     */
    public static ViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId);
        } else {
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * 获取Holder中的ItemView
     */
    public <T extends View> T getView(int viewId) {
        View item = mViews.get(viewId);
        if (item == null) {
            item = mConvertView.findViewById(viewId);
            mViews.put(viewId, item);
        }
        return (T) item;
    }

    /**
     * 获取ConvertView
     */
    public View getMConvertView() {
        return mConvertView;
    }
}