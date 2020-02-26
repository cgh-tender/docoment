package cn.com.LoginController.controller;

import cn.com.LoginController.LoginControllerFactory;
import cn.com.entity.MyToken;
import cn.com.support.SpringSuperController;
import cn.com.utils.AuthFilterItemProperties;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Log4j
public class TokenLogin extends SpringSuperController implements LoginControllerFactory {

    @Resource
    public AuthFilterItemProperties authFilterItemProperties;

    @Override
    public String doLogin() {
        log.info("not session login");
        MyToken myToken = new MyToken("admin","1");
        try{
            Subject subject = SecurityUtils.getSubject();
            subject.login(myToken);
        }catch (AuthenticationException ae){
            throw new AuthenticationException("登录异常: "+ae.getMessage());
        }
        getResponse().addHeader("TOKEN",myToken.toString());

        return authFilterItemProperties.getLOGIN();
    }

    @Override
    public int getType() {
        return 2;
    }
}
