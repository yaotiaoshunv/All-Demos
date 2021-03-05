package com.lzw.recyclerviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
public class ListViewRecyclerViewAdapter extends BaseRecyclerViewAdapter {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_LOADER_MORE = 1;
    public OnRefreshListener mOnRefreshListener;

    public ListViewRecyclerViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = getSubView(parent, viewType);
        if (viewType == TYPE_NORMAL) {
            return new InnerViewHolder(view);
        } else {
            LoaderMoreHolder loaderMoreHolder = new LoaderMoreHolder(view);
            loaderMoreHolder.update(LoaderMoreHolder.LOADER_STATE_LOADING);
            return loaderMoreHolder;
        }
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_NORMAL) {
            view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_load_more, null);
        }
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL && holder instanceof InnerViewHolder) {
            ((InnerViewHolder) holder).setData(mData.get(position), position);
        } else {
            ((LoaderMoreHolder) holder).update(LoaderMoreHolder.LOADER_STATE_LOADING);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_LOADER_MORE;
        }
        return TYPE_NORMAL;
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    public interface OnRefreshListener {
        void onUpPullRefresh(LoaderMoreHolder loaderMoreHolder);
    }

    public class LoaderMoreHolder extends RecyclerView.ViewHolder {
        public static final int LOADER_STATE_LOADING = 0;
        public static final int LOADER_STATE_RELOAD = 1;
        public static final int LOADER_STATE_NORMAL = 2;

        LinearLayout llLoading;
        TextView tvReload;

        public LoaderMoreHolder(@NonNull View itemView) {
            super(itemView);

            llLoading = itemView.findViewById(R.id.ll_loading);
            tvReload = itemView.findViewById(R.id.tv_reload);
            tvReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(LOADER_STATE_LOADING);
                }
            });
        }

        public void update(int state) {
            //重置控件状态
            llLoading.setVisibility(View.GONE);
            tvReload.setVisibility(View.GONE);
            switch (state) {
                case LOADER_STATE_LOADING:
                    llLoading.setVisibility(View.VISIBLE);
                    //触发加载数据
                    startLoadMore();
                    break;
                case LOADER_STATE_RELOAD:
                    tvReload.setVisibility(View.VISIBLE);
                    break;
                case LOADER_STATE_NORMAL:
                    llLoading.setVisibility(View.GONE);
                    tvReload.setVisibility(View.GONE);
                    break;
                default:
            }
        }

        private void startLoadMore() {
            if (mOnRefreshListener != null) {
                mOnRefreshListener.onUpPullRefresh(this);
            }
        }
    }
}
