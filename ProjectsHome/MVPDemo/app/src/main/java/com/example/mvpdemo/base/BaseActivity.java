package com.example.mvpdemo.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements View.OnClickListener {

    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentViewId());

        initData();
        initViews();
        initListener();

        mPresenter = getMPresenterInstance();
        mPresenter.bindView(this);
    }

    /**
     * 让子类指定具体要实例化哪个布局
     * @return
     */
    public abstract int getContentViewId();

    /**
     * 初始数据
     */
    public abstract void initData();

    /**
     * 初始化Views
     */
    public abstract void initViews();

    /**
     * 初始化View的监听事件
     */
    public abstract void initListener();

    public abstract P getMPresenterInstance();

    /**
     * 处理，响应错误信息
     */
    public abstract <ERROR extends Object> void responseError(ERROR error, Throwable throwable);

    @Override
    protected void onDestroy() {
        super.onDestroy();

        destory();
    }

    /**
     * 既然有了初始化的操作（初始了view和监听），就应该想到要有对应的销毁
     */
    public abstract void destory();
}
