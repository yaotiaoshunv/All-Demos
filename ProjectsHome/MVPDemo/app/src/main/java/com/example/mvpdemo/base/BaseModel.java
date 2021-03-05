package com.example.mvpdemo.base;

public class BaseModel<P extends BasePresenter> {

    public P mPresenter;

    public BaseModel(P mPresenter) {
        this.mPresenter = mPresenter;
    }
}
