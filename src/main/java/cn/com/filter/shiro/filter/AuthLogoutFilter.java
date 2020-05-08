package cn.com.filter.shiro.filter;

import cn.com.SpringContextUtil;
import cn.com.entity.Result;
import cn.com.filter.token.TokenServiceFactory;
import cn.com.utils.ex.LogOutException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j
public class AuthLogoutFilter extends LogoutFilter {
    public AuthLogoutFilter() {
        super();
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.info(">>>>>>>>>>>>>>>登出 AuthLogoutFilter<<<<<<<<<<<<<");
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String token = servletRequest.getHeader("TOKEN");
        if (StringUtils.isNotBlank(token)){
            log.info("当前为登出操作 有 TOKEN ");
            TokenServiceFactory bean = SpringContextUtil.getBean(TokenServiceFactory.class);
            bean.getInstance().builder(token).clear();//清空存储
            servletRequest.removeAttribute("TOKEN");
        }
        try {
            Subject subject=getSubject(request,response);
            subject.logout();
        }catch (LogOutException e){
            throw new LogOutException("登出异常");
        }
        Result.success((HttpServletResponse) response,"登出成功");
        // 是否向下进行走 过滤器
        return false;
    }
}
