package com.example.mvpdemo.login;

/**
 * Login的契约合同
 *
 * 问：以OOP思想考虑，契约合同，能够带来什么好处？
 * 答：低耦合，接口的统一管理，业务逻辑清晰，易于后期维护
 */
public interface ILogin {
    interface M {
        void requestLogin(String name, String password) throws Exception;
    }

    interface VP {
        void requestLogin(String name, String password);

        void responseLoginResult(boolean loginStatusResult);
    }
}
