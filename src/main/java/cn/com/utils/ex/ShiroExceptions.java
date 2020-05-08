package cn.com.utils.ex;

import lombok.extern.log4j.Log4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j
public class ShiroExceptions extends BaseExceptions {

    //不正确的凭证
    @ExceptionHandler({IncorrectCredentialsException.class})
    public void incorrectCredentialsException(IncorrectCredentialsException ex){
        resultFormat(15,"不正确的凭证");
    }

    //凭证过期
    @ExceptionHandler({ExpiredCredentialsException.class})
    public void expiredCredentialsException(ExpiredCredentialsException ex){
        resultFormat(16,"凭证过期");
    }

    //未知的账号
    @ExceptionHandler({UnknownAccountException.class})
    public void unknownAccountException(UnknownAccountException ex){
        resultFormat(17,"未知的账号");
    }

    //认证次数超过限制
    @ExceptionHandler({ExcessiveAttemptsException.class})
    public void excessiveAttemptsException(ExcessiveAttemptsException ex){
        resultFormat(18,ex.getMessage());
    }

    //禁用的账号
    @ExceptionHandler({DisabledAccountException.class})
    public void disabledAccountException(DisabledAccountException ex){
        resultFormat(19,"账号禁用");
    }

    //账号被锁定
    @ExceptionHandler({LockedAccountException.class})
    public void lockedAccountException(LockedAccountException ex){
        resultFormat(20,"账号被锁定");
    }

    //凭证异常
    @ExceptionHandler({CredentialsException.class})
    public void credentialsException(CredentialsException ex){
        resultFormat(21,"凭证异常");
    }

    //账号异常
    @ExceptionHandler({AccountException.class})
    public void accountException(AccountException ex){
        resultFormat(22,"账号异常");
    }

    //Token 不正确
    @ExceptionHandler({UnsupportedTokenException.class})
    public void unsupportedTokenException(UnsupportedTokenException ex){
        resultFormat(23,"账号异常");
    }

    //Token 不正确
    @ExceptionHandler({UnauthorizedException.class})
    public void unauthorizedException(UnauthorizedException ex){
        resultFormat(24,"请求的操作或对请求的资源的访问是不允许的");
    }

    //登录异常
    @ExceptionHandler({AuthenticationException.class})
    public void authenticationException(AuthenticationException ex){
        resultFormat(25,ex.getMessage());
    }

    //没有登录
    @ExceptionHandler({LogOutException.class})
    public void logOutException(LogOutException ex){
        resultFormat(26,"登录异常:请重新登录");
    }

    //登录异常IP不一致问题
    @ExceptionHandler({LogIPException.class})
    public void logIPException(LogIPException ex){
        resultFormat(27,"登录异常:IP不一致问题与申请不一致问题");
    }
    //登录异常IP不一致问题
    @ExceptionHandler({AppConfigException.class})
    public void appConfigException(AppConfigException ex){
        resultFormat(28,"登录异常:配置文件出现异常");
    }


}
