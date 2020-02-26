package cn.com.LoginController;

public interface LoginControllerFactory {
    int getType();
    String doLogin();
}
