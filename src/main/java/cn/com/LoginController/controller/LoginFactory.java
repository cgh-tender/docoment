package cn.com.LoginController.controller;

import cn.com.LoginController.LoginControllerFactory;
import cn.com.LoginController.LoginTFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginFactory implements LoginTFactory {

    private Map<Integer, LoginControllerFactory> data = new ConcurrentHashMap<>();

    public LoginFactory(List<LoginControllerFactory> services){
        services.forEach(c -> {
            data.put(c.getType(),c);
        });
    }

    @Override
    public LoginControllerFactory getInstance(int type) {
        return data.get(type);
    }
}
