package com.example.mvpdemo.base;

/**
 * 作为view层（Activity作为View层）和model层的交互类。
 * @param <V>
 * @param <M>
 */
public abstract class BasePresenter<V extends BaseActivity, M extends BaseModel> {

    public V mView;

    public M mModel;

    public BasePresenter() {
        this.mModel = getMModelInstance();
    }

    public void bindView(V mView) {
        this.mView = mView;
    }

    /**
     * 有绑定就应该想到解绑，避免内存溢出
     */
    public void unBindView() {
        this.mView = null;
    }

    public abstract M getMModelInstance();
}
