package cn.com.controller;

import cn.com.entity.MyToken;
import cn.com.support.SpringSuperController;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j
@RestController
public class UserController extends SpringSuperController{

    @RequestMapping("/login")
    public String doLogin(){
        MyToken myToken = new MyToken("admin","1");
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(myToken);
            getResponse().addHeader("TOKEN",myToken.toString());
            log.info("login");
        }catch (Exception e){
            e.printStackTrace();
        }

        return "login";
    }

}