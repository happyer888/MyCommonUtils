package com.framework.ui.keithly.uiframework.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.framework.ui.keithly.uiframework.bean.FootBean;
import com.framework.ui.keithly.uiframework.bean.HeadBean;
import com.framework.ui.keithly.uiframework.interfaces.BodyType;
import com.framework.ui.keithly.uiframework.interfaces.FootType;
import com.framework.ui.keithly.uiframework.interfaces.HeadType;
import com.framework.ui.keithly.uiframework.interfaces.ItemType;
import com.framework.ui.keithly.uiframework.ui.view.FootViewLayout;
import com.framework.ui.keithly.uiframework.viewholder.FinalViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的终极万能适配器
 * <p>
 * 注:需要添加 V7-design依赖库;
 */
public class FinalAdapter extends RecyclerView.Adapter<FinalViewHolder> {

    private List<ItemType> mShowItems = new ArrayList<>();
    private int mBodyLayout = 0;
    private AdapterListener mAdapterListener;
    private FootViewLayout mFootViewLayout;

    /**
     * Instantiates a new Final adapter.
     * 终极适配器构造
     *
     * @param showItems       the show items    传入ItemType类型数据集合
     * @param bodyLayout      the body layout   传入Body内容区域的布局ID
     * @param adapterListener the adapter listener  传入一个适配器监听器
     */
    public FinalAdapter(List<ItemType> showItems, int bodyLayout, AdapterListener adapterListener) {
        this.mShowItems = showItems;
        this.mBodyLayout = bodyLayout;
        this.mAdapterListener = adapterListener;
    }

    /**
     * 获取ViewHolder对象
     *
     * @param parent   子条目View的父布局
     * @param viewType 传入子条目的类型(ItemType)
     * @return 返回一个ViewHolder对象
     */
    @Override
    public FinalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据不同的条目类型展示不同的view
        View view = null;
        switch (viewType) {
            case HEADTYPE:
                //使用钩子方法,但要注意有一个小的隐患!
                view = LayoutInflater.from(parent.getContext()).inflate(mHeadView, parent, false);
                break;
            case BODYTYPE:
                if (mBodyLayout <= 0) {
                    throw new RuntimeException("这里需要传入item条目布局!!!");
                }
                view = LayoutInflater.from(parent.getContext()).inflate(mBodyLayout, parent, false);
                break;
            case FOOTTYPE:
                mFootViewLayout = new FootViewLayout(parent.getContext());
                //还可以设置根据状态切换底部View布局
                //footViewLayout.changeView(FootViewLayout.FOOTSTAUTS.NOMORE);
                view = mFootViewLayout;
                break;
            default:
                break;
        }
        return new FinalViewHolder(view);
    }

    /**
     * 数据更新,UI显示
     * 在此设置子条目View的点击事件
     *
     * @param holder   传入ViewHolder对象
     * @param position 条目的索引
     */
    @Override
    public void onBindViewHolder(final FinalViewHolder holder, final int position) {
        //设置子条目view的点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterItemClick.onItemClick(holder, position, mShowItems);
            }
        });

        //获取条目类型
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case HEADTYPE:
                mAdapterListener.bindHeadView(holder, position);
                break;
            case BODYTYPE:
                mAdapterListener.bindBodyView(mShowItems, holder, position);
                break;
            case FOOTTYPE:
                mAdapterListener.bindFootView(mFootViewLayout);
                break;
            default:
                break;
        }
    }

    /**
     * 列表展示的条目数量
     *
     * @return 返回条目Int数
     */
    @Override
    public int getItemCount() {
        return mShowItems.size();
    }

    /**
     * 定义多条目类型.
     * 头部类型Head
     */
    public static final int HEADTYPE = 0;//头部
    /**
     * 内容类型Body
     */
    public static final int BODYTYPE = 1;//内容
    /**
     * 底部类型Foot
     */
    public static final int FOOTTYPE = 2;//底部

    /**
     * 实现多条目方法覆写
     *
     * @param position 条目索引
     * @return 返回条目类型
     */
    @Override
    public int getItemViewType(int position) {
        if (mShowItems.get(position) instanceof HeadType) {
            return HEADTYPE;
        }
        if (mShowItems.get(position) instanceof BodyType) {
            return BODYTYPE;
        }
        if (mShowItems.get(position) instanceof FootType) {
            return FOOTTYPE;
        }
        return BODYTYPE;
    }

    //定义头部布局
    private int mHeadView = 0;

    /**
     * 设置显示头部布局
     *
     * @param headView the head view    传入头部布局资源ID
     */
    public void setHeadView(int headView) {
        mHeadView = headView;
    }

    /**
     * The interface Adapter listener.
     * 接口回调
     */
    public interface AdapterListener {
        /**
         * Bind head view.
         * 绑定头部布局View
         *
         * @param holder   the holder
         * @param position the position
         */
        void bindHeadView(FinalViewHolder holder, final int position);

        /**
         * Bind body view.
         * 绑定内容布局View
         *
         * @param showItems the show items
         * @param holder    the holder
         * @param position  the position
         */
        void bindBodyView(List<ItemType> showItems, FinalViewHolder holder, final int position);

        /**
         * Bind foot view.
         * 绑定底部布局View
         *
         * @param mFootViewLayout the m foot view layout
         */
        void bindFootView(FootViewLayout mFootViewLayout);
    }

    /**
     * 刷新数据自动加载头部或底部
     */
    public void updateData() {
        //不管以前有没有,都删除
        for (int i = 0; i < mShowItems.size(); i++) {
            if (mShowItems.get(i) instanceof HeadType) {
                mShowItems.remove(i);
            }
        }

        //需要添加头部
        if (mHeadView > 0) {
            mShowItems.add(0, new HeadBean());
        }

        //移除底部
        for (int i = 0; i < mShowItems.size(); i++) {
            if (mShowItems.get(i) instanceof FootType) {
                mShowItems.remove(i);
            }
        }

        //根据是否需要底部来添加数据
        if (isFootShow) {
            //true说明需要显示底部
            mShowItems.add(new FootBean());
        }

        //刷新数据
        notifyDataSetChanged();
    }

    private boolean isFootShow = false;//默认false不显示底部

    /**
     * 是否需要显示底部.
     *
     * @param isShow the is show
     */
    public void isShowFoot(boolean isShow) {
        isFootShow = isShow;
    }

    /**
     * The interface Adapter item click.
     * 条目的点击事件
     */
    public interface AdapterItemClick {
        /**
         * On item click.
         * 条目点击事件
         *
         * @param holder    the holder  ViewHolder对象
         * @param position  the position    条目的索引
         * @param showItems the show items  ItemType数据集合
         */
        void onItemClick(FinalViewHolder holder, int position, List<ItemType> showItems);
    }

    private AdapterItemClick mAdapterItemClick;

    /**
     * Sets item click listener.
     * 设置条目的点击事件监听
     *
     * @param adapterItemClick the adapter item click
     */
    public void setAdapterItemClickListener(AdapterItemClick adapterItemClick) {
        this.mAdapterItemClick = adapterItemClick;
    }
}