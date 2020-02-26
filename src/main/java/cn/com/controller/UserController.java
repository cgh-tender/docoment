package cn.com.controller;

import cn.com.SimpleService.AService;
import cn.com.SimpleService.AServiceFactory;
import cn.com.SimpleService.AServiceSFactory;
import cn.com.entity.MyToken;
import cn.com.support.SpringSuperController;
import cn.com.utils.AuthFilterItemProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Log4j
//@RestController
public class UserController extends SpringSuperController{
    @Resource
    private AuthFilterItemProperties authFilterItemProperties;

    @Resource
    private AServiceSFactory service;

//    @RequestMapping("/login")
    public String doLogin(){
        service.getInstance("Mysql").create();
        return authFilterItemProperties.getLOGIN();
    }

//    @RequestMapping("/frontend/login")
    public String doLogin1(){
        MyToken myToken = new MyToken("admin","1");
        try{
            Subject subject = SecurityUtils.getSubject();
            subject.login(myToken);
        }catch (AuthenticationException ae){
            throw new AuthenticationException("登录异常: "+ae.getMessage());
        }
        getResponse().addHeader("TOKEN",myToken.toString());
        log.info("login");
        return authFilterItemProperties.getLOGIN();
    }

//    @RequestMapping("/index")
    public String index(){
        MyToken myToken = new MyToken("admin","1");
        try{
            Subject subject = SecurityUtils.getSubject();
            subject.login(myToken);
        }catch (AuthenticationException ae){
            throw new AuthenticationException("登录异常: "+ae.getMessage());
        }
        getResponse().addHeader("TOKEN",myToken.toString());
        log.info("login");
        return authFilterItemProperties.getINDEX();
    }
}