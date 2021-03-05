package com.lzw.recyclerviewdemo;

import android.view.View;
import android.view.ViewGroup;

import com.lzw.recyclerviewdemo.adapters.BaseRecyclerViewAdapter;
import com.lzw.recyclerviewdemo.beans.ItemBean;

import java.util.List;

/**
 *
 * @author Li Zongwei
 * @date 2020/11/26
 **/
public class StaggerAdapter extends BaseRecyclerViewAdapter {

    public StaggerAdapter(List<ItemBean> data) {
        super(data);
    }

    @Override
    protected View getSubView(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_stagger_view,null);
        return view;
    }
}
