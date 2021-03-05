package com.example.mvpdemo.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvpdemo.R;
import com.example.mvpdemo.base.BaseActivity;
import com.example.mvpdemo.base.BasePresenter;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILogin.VP{

    private EditText etName;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    public void initListener() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public LoginPresenter getMPresenterInstance() {
        return new LoginPresenter();
    }

    @Override
    public <ERROR> void responseError(ERROR error, Throwable throwable) {
        Toast.makeText(this,"" + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void destory() {

    }

    @Override
    public void onClick(View view) {
        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        switch (view.getId()){
            case R.id.btn_login:
                requestLogin(name,password);
                break;
            default:
                break;
        }
    }

    @Override
    public void requestLogin(String name, String password) {
        mPresenter.requestLogin(name,password);
    }

    @Override
    public void responseLoginResult(boolean loginStatusResult) {
        Toast.makeText(this,loginStatusResult?"登录成功":"登录失败",Toast.LENGTH_SHORT).show();
    }
}
