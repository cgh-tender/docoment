package cn.com.LoginController.controller;

import cn.com.LoginController.LoginControllerFactory;
import cn.com.SimpleService.AServiceSFactory;
import cn.com.support.SpringSuperController;
import cn.com.utils.AuthFilterItemProperties;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Log4j
public class SessionLogin extends SpringSuperController implements LoginControllerFactory {
    @Resource
    public AuthFilterItemProperties authFilterItemProperties;

    @Resource
    private AServiceSFactory service;

    @Override
    public String doLogin() {
        log.info("session login");
        service.getInstance("Mysql").create();
        return authFilterItemProperties.getLOGIN();
    }

    @Override
    public int getType() {
        return 1;
    }
}
