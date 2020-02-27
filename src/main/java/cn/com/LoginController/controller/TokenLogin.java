package cn.com.LoginController.controller;

import cn.com.LoginController.LoginControllerFactory;
import cn.com.filter.token.Body.Impl.TokenUserNamePayload;
import cn.com.filter.token.TokenServiceFactory;
import cn.com.support.SpringSuperController;
import cn.com.utils.AuthFilterItemProperties;
import cn.com.utils.interfaceRun.YmlPropertySourceComponent;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@Log4j
@YmlPropertySourceComponent(value = {"classpath:bootstrap.yml"})
public class TokenLogin extends SpringSuperController implements LoginControllerFactory {

    @Resource
    private TokenServiceFactory tokenServiceFactory;
    @Resource
    public AuthFilterItemProperties authFilterItemProperties;
    @Override
    public String doLogin() {
        log.info("not session login");
        TokenUserNamePayload token = new TokenUserNamePayload("admin", "1", getRequest());
        String SToken = tokenServiceFactory.getInstance().builder(token).getToken();
        log.info(SToken);
        tokenServiceFactory.getInstance().builder(SToken).saveToken();
        try{
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        }catch (AuthenticationException ae){
            throw new AuthenticationException("登录异常: "+ae.getMessage());
        }
        getResponse().addHeader("TOKEN",SToken);

        return authFilterItemProperties.getLOGIN();
    }

    @Override
    public int getType() {
        return 2;
    }
}
