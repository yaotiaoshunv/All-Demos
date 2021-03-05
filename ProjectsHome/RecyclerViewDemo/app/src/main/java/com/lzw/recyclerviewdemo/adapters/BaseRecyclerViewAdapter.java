package com.lzw.recyclerviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzw.recyclerviewdemo.R;
import com.lzw.recyclerviewdemo.beans.ItemBean;

import java.util.List;

/**
 * @author Li Zongwei
 * @date 2020/11/25
 **/
public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final List<ItemBean> mData;
    private OnItemClickListener mOnItemClickListener;

    /**
     * 通过构造方法传入数据
     * 思考：
     * 1、数据直接在adapter内部初始化怎么样？
     * 1）不好。数据放里面，适配器就定死了。不够灵活。
     * 2）从设计模式的角度思考，适配器的职责是建立数据和视图的关系。而数据从何而来，如何产生，是不应该关注的。
     */
    public BaseRecyclerViewAdapter(List<ItemBean> data) {
        this.mData = data;
    }

    /**
     * 创建条目View
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        return new InnerViewHolder(view);
    }

    /**
     * 子类传入View
     *
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract View getSubView(ViewGroup parent, int viewType);

    /**
     * 用于绑定holder，一般用来设置数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((InnerViewHolder)holder).setData(mData.get(position), position);
    }

    /**
     * 获取返回条目的个数
     *
     * @return 返回条目的个数
     */
    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        // 设置一个监听，即设置一个回调接口
        this.mOnItemClickListener = listener;
    }

    /**
     * 编写回调的步骤
     * 1、创建接口
     * 2、定义接口内部方法
     * 3、提供设置接口的方法（外部实现接口）
     * 4、接口方法调用
     */
    public interface OnItemClickListener {
        /**
         * 处理item点击事件
         *
         * @param position
         */
        void onItemClick(int position);
    }

    /**
     * 思考：
     * 1、RecycleView的自定义视频器中使用的自定义内部ViewHolder是否要设为static？why？
     */
    public class InnerViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvTitle;
        private int mPosition;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);

            //找到条目的控件
            ivIcon = itemView.findViewById(R.id.icon);
            tvTitle = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mPosition);
                    }
                }
            });
        }

        /**
         * 给item设置数据
         *
         * @param itemBean
         */
        public void setData(ItemBean itemBean, int position) {
            this.mPosition = position;
            ivIcon.setImageResource(itemBean.icon);
            tvTitle.setText(itemBean.text);
        }
    }
}
