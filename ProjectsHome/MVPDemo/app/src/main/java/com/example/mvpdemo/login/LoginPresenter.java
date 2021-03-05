package com.example.mvpdemo.login;

import com.example.mvpdemo.base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginActivity,LoginModel> implements ILogin.VP {
    @Override
    public LoginModel getMModelInstance() {
        return new LoginModel(this);
    }

    @Override
    public void requestLogin(String name, String password) {
        //TODO:校验请求的信息，进行逻辑处理
        try {
            mModel.requestLogin(name,password);
        } catch (Exception e) {
            e.printStackTrace();

            //TODO:异常的处理
            // 如：保存到日志
            // 。。。
        }
    }

    @Override
    public void responseLoginResult(boolean loginStatusResult) {
        //TODO:真实开发过程中，需要解析数据
        //。。。

        mView.responseLoginResult(loginStatusResult);
    }
}
