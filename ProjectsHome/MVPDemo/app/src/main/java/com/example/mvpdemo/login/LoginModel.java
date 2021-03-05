package com.example.mvpdemo.login;

import com.example.mvpdemo.base.BaseModel;

public class LoginModel extends BaseModel<LoginPresenter> implements ILogin.M{
    public LoginModel(LoginPresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void requestLogin(String name, String password) throws Exception {
        //TODO:请求服务器的登录接口，然后拿到服务器返回的JSON数据
        //。。。

        if ("abd".equals(name) && "123".equals(password)){
            mPresenter.responseLoginResult(true);
        }else {
            mPresenter.responseLoginResult(false);
        }
    }
}
