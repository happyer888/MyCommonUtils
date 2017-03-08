import android.support.v4.widget.DrawerLayout;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 万能适配器(FinalAdapter)
 */
public class FinalAdapter<T> extends BaseAdapter {

    private List<T> mShowItems = new ArrayList<>();
    private int mLayoutId = 0;
    private AdapterListener mAdapterListener;

    public FinalAdapter(List<T> showItems, int layoutId, AdapterListener adapterListener) {
        this.mShowItems = showItems;
        this.mLayoutId = layoutId;
        this.mAdapterListener = adapterListener;
    }

    @Override
    public int getCount() {
        return mShowItems == null ? 0 : mShowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mShowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FinalViewHolder finalViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), mLayoutId, null);
            finalViewHolder = new FinalViewHolder(convertView);
            convertView.setTag(finalViewHolder);
        } else {
            finalViewHolder = (FinalViewHolder) convertView.getTag();
        }
        //绑定数据
        bindView(finalViewHolder, mShowItems.get(position));

        return convertView;
    }

    //绑定数据
    private void bindView(FinalViewHolder finalViewHolder, T content) {
        mAdapterListener.bindView(finalViewHolder, content);
    }

    //适配器监听接口
    public interface AdapterListener<T> {
        void bindView(FinalViewHolder finalViewHolder, T content);
    }

    //创建ViewHolder
    public static class FinalViewHolder {
        public View mLayoutView;

        public FinalViewHolder(View layoutView) {
            this.mLayoutView = layoutView;
        }

        //SparseArray性能更高,Key限定为int类型
        private SparseArray<View> mViewHashMap = new SparseArray<>();

        /**
         * 通过ID获取View控件
         *
         * @param id 控件View的ID
         * @return 返回View控件
         */
        public View getViewById(int id) {
            View view = mViewHashMap.get(id);
            if (view == null) {
                view = mLayoutView.findViewById(id);
                mViewHashMap.put(id, view);
            }
            return view;
        }

        //根据需求,增加定义的类型,可以简化强置转换!
        public Button getButton(int id) {
            return (Button) getViewById(id);
        }

        public TextView getTextView(int id) {
            return (TextView) getViewById(id);
        }

        public EditText getEditText(int id) {
            return (EditText) getViewById(id);
        }

        public ImageView getImageView(int id) {
            return (ImageView) getViewById(id);
        }

        public ListView getListView(int id) {
            return (ListView) getViewById(id);
        }

        public CheckBox getCheckBox(int id) {
            return (CheckBox) getViewById(id);
        }

        public ScrollView getScrollView(int id) {
            return (ScrollView) getViewById(id);
        }

        public FrameLayout getFrameLayout(int id) {
            return (FrameLayout) getViewById(id);
        }

        public ProgressBar getProgressBar(int id) {
            return (ProgressBar) getViewById(id);
        }

        public DrawerLayout getDrawerLayout(int id) {
            return (DrawerLayout) getViewById(id);
        }

        public RelativeLayout getRelativeLayout(int id) {
            return (RelativeLayout) getViewById(id);
        }

        public ExpandableListView getExpandableListView(int id) {
            return (ExpandableListView) getViewById(id);
        }
    }
}
