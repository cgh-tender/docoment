package cn.com.LoginController.controller;

import cn.com.LoginController.LoginTFactory;
import cn.com.support.SpringSuperController;
import cn.com.utils.AuthFilterItemProperties;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Log4j
public class LoginSupperController extends SpringSuperController{
    @Resource
    public AuthFilterItemProperties authFilterItemProperties;

    @Resource
    private LoginTFactory loginTFactory;

    @RequestMapping("/login")
    public String login() {
        return loginTFactory.getInstance(authFilterItemProperties.getIsSeparation()).doLogin();
    }

}
