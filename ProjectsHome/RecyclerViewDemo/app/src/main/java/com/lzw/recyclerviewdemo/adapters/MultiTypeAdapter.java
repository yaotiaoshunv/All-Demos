package com.lzw.recyclerviewdemo.adapters;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzw.recyclerviewdemo.R;
import com.lzw.recyclerviewdemo.beans.MultiTypeBean;

import java.util.List;

/**
 * @author Li Zongwei
 * @date 2020/11/26
 **/
public class MultiTypeAdapter extends RecyclerView.Adapter {

    public static final int FULL_IMAGE = 0;
    public static final int THREE_IMAGES = 1;
    public static final int RIGHT_IMAGE = 2;

    private final List<MultiTypeBean> mData;

    public MultiTypeAdapter(List<MultiTypeBean> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == FULL_IMAGE) {
            view = View.inflate(parent.getContext(), R.layout.item_grid_view, null);
            return new FullImageHolder(view);
        } else if (viewType == THREE_IMAGES) {
            view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
            return new ThreeImagesHolder(view);
        } else {
            view = View.inflate(parent.getContext(), R.layout.item_stagger_view, null);
            return new RightImageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();
        if (type == FULL_IMAGE) {
            ((FullImageHolder) holder).setData(mData.get(position));
        } else if (type == THREE_IMAGES) {
            ((ThreeImagesHolder) holder).setData(mData.get(position));
        } else {
            ((RightImageHolder) holder).setData(mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        MultiTypeBean typeBean = mData.get(position);
        return typeBean.type;
    }

    private class FullImageHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;

        public FullImageHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.icon);
        }

        public void setData(MultiTypeBean multiTypeBean) {
            ivIcon.setImageResource(multiTypeBean.pic);
        }
    }

    private class ThreeImagesHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;

        public ThreeImagesHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.icon);
        }

        public void setData(MultiTypeBean multiTypeBean) {
            ivIcon.setImageResource(multiTypeBean.pic);
        }
    }

    private class RightImageHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;

        public RightImageHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.icon);
        }

        public void setData(MultiTypeBean multiTypeBean) {
            ivIcon.setImageResource(multiTypeBean.pic);
        }
    }
}
