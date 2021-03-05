package com.lzw.recyclerviewdemo.adapters;

import android.view.View;
import android.view.ViewGroup;

import com.lzw.recyclerviewdemo.R;
import com.lzw.recyclerviewdemo.beans.ItemBean;

import java.util.List;

/**
 * @author Li Zongwei
 * @date 2020/11/25
 **/
public class GridViewRecyclerViewAdapter extends BaseRecyclerViewAdapter {

    public GridViewRecyclerViewAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_grid_view, null);
        return view;
    }
}
