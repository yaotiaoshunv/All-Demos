package com.lzw.recyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lzw.recyclerviewdemo.adapters.BaseRecyclerViewAdapter;
import com.lzw.recyclerviewdemo.adapters.GridViewRecyclerViewAdapter;
import com.lzw.recyclerviewdemo.adapters.ListViewRecyclerViewAdapter;
import com.lzw.recyclerviewdemo.beans.ItemBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Li Zongwei
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView rv;
    private List<ItemBean> itemData;
    private BaseRecyclerViewAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        rv = findViewById(R.id.recycler_view);

        initData();

        showList(true, false);

        //处理下拉刷新
        handlerDownPullUpdate();
    }

    private void handlerDownPullUpdate() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里执行刷新数据的操作

                //下拉时触发，此方法在主线程中执行

                ItemBean itemBean = new ItemBean();
                itemBean.icon = R.mipmap.p1;
                itemBean.text = "新添加的";
                itemData.add(0, itemBean);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //刷新停止，更新列表
                        mAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000L);
            }
        });
    }

    private void initData() {
        // 整体流程：准备数据->创建适配器->设置适配器->设置RV样式->显示

        // step.2 准备数据
        itemData = new ArrayList<>();
        for (int i = 0; i < Datas.icons.length; i++) {
            ItemBean item = new ItemBean();
            item.icon = Datas.icons[i];
            item.text = "第 " + i + "条数据";
            itemData.add(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            //ListView的部分
            case R.id.list_view_vertical_stander:
                Log.v(TAG, "点击了ListView垂直标准");
                showList(true, false);
                break;
            case R.id.list_view_vertical_reverse:
                showList(true, true);
                break;
            case R.id.list_view_horizontal_stander:
                showList(false, false);
                break;
            case R.id.list_view_horizontal_reverse:
                showList(false, true);
                break;

            //GridView的部分
            case R.id.grid_view_vertical_stander:
                showGrid(true, false);
                break;
            case R.id.grid_view_vertical_reverse:
                showGrid(true, true);
                break;
            case R.id.grid_view_horizontal_stander:
                showGrid(false, false);
                break;
            case R.id.grid_view_horizontal_reverse:
                showGrid(false, true);
                break;

            //瀑布流部分
            case R.id.stagger_view_vertical_stander:
                showStagger(true, false);
                break;
            case R.id.stagger_view_vertical_reverse:
                showStagger(true, true);
                break;
            case R.id.stagger_view_horizontal_stander:
                showStagger(false, false);
                break;
            case R.id.stagger_view_horizontal_reverse:
                showStagger(false, true);
                break;

            case R.id.multi_type:
                Intent intent = new Intent(MainActivity.this, MultiTypeActivity.class);
                startActivity(intent);
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示ListView的效果
     */
    private void showList(boolean isVertical, boolean isReverse) {
        // step.3 创建适配器
        mAdapter = new ListViewRecyclerViewAdapter(itemData);

        // step.4 为RecyclerView设置适配器
        rv.setAdapter(mAdapter);

        // step.5 设置rv样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // step.5.1 设置水平/垂直
        layoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        // step.5.2 设置正向/反向
        layoutManager.setReverseLayout(isReverse);
        rv.setLayoutManager(layoutManager);
        initListener();

    }

    /**
     * 显示GridView的效果
     *
     * @param isVertical
     * @param isReverse
     */
    private void showGrid(boolean isVertical, boolean isReverse) {
        mAdapter = new GridViewRecyclerViewAdapter(itemData);
        rv.setAdapter(mAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        gridLayoutManager.setReverseLayout(isReverse);
        rv.setLayoutManager(gridLayoutManager);
        initListener();
    }

    /**
     * 显示瀑布流效果
     *
     * @param isVertical
     */
    private void showStagger(boolean isVertical, boolean isReverse) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
        staggeredGridLayoutManager.setReverseLayout(isReverse);
        rv.setLayoutManager(staggeredGridLayoutManager);

        mAdapter = new StaggerAdapter(itemData);
        rv.setAdapter(mAdapter);
        initListener();
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
            }
        });

        //处理上拉加载更多
        if (mAdapter instanceof ListViewRecyclerViewAdapter) {
            ((ListViewRecyclerViewAdapter) mAdapter).setOnRefreshListener(new ListViewRecyclerViewAdapter.OnRefreshListener() {
                @Override
                public void onUpPullRefresh(final ListViewRecyclerViewAdapter.LoaderMoreHolder loaderMoreHolder) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //刷新停止，更新列表
                            Random random = new Random();
                            if (random.nextInt() % 2 == 0) {
                                ItemBean itemBean = new ItemBean();
                                itemBean.icon = R.mipmap.p1;
                                itemBean.text = "新添加的";
                                itemData.add(itemBean);

                                mAdapter.notifyDataSetChanged();
                                loaderMoreHolder.update(ListViewRecyclerViewAdapter.LoaderMoreHolder.LOADER_STATE_NORMAL);
                            }else {
                                loaderMoreHolder.update(ListViewRecyclerViewAdapter.LoaderMoreHolder.LOADER_STATE_RELOAD);
                            }
                        }
                    }, 2000L);
                }
            });
        }
    }
}