package com.framework.ui.keithly.uiframework.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ViewHolder类
 * 用途:根据传入的资源id查找对应的View控件
 */
public class FinalViewHolder extends RecyclerView.ViewHolder {
    public FinalViewHolder(View itemView) {
        super(itemView);
    }

    private SparseArray<View> mViewSparseArray = new SparseArray<>();

    /**
     * 根据resID查找控件
     *
     * @param resid 传入查找的资源ID
     * @return 返回View对象
     */
    public View getViewById(int resid) {
        View view = mViewSparseArray.get(resid);
        if (view == null) {
            view = itemView.findViewById(resid);
            mViewSparseArray.put(resid, view);
        }
        return view;
    }

    /**
     * 查找TextView控件
     *
     * @param resid 传入控件的资源ID
     * @return 返回控件对象
     */
    public TextView getTextViewById(int resid) {
        return (TextView) getViewById(resid);
    }

    /**
     * 查找ImageView控件
     *
     * @param resid 传入控件的资源ID
     * @return 返回控件对象
     */
    public ImageView getImageViewById(int resid) {
        return (ImageView) getViewById(resid);
    }

    /**
     * 查找Button控件
     *
     * @param resid 传入控件的资源ID
     * @return 返回控件对象
     */
    public Button getButtonById(int resid) {
        return (Button) getViewById(resid);
    }
}
