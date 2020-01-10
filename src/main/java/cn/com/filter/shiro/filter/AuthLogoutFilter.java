package cn.com.filter.shiro.filter;

import cn.com.SpringContextUtil;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.SessionException;
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
        setRedirectUrl("/login");
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.info(">>>>>>>>>>>>>>>登出 AuthLogoutFilter<<<<<<<<<<<<<");
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String token = servletRequest.getHeader("TOKEN");
        if (StringUtils.isNotBlank(token)){
            log.info("当前为登出操作 有 TOKEN ");
            servletRequest.removeAttribute("TOKEN");
        }
        Subject subject=getSubject(request,response);
//        String redirectUrl=getRedirectUrl(request,response,subject);
//        ServletContext context= request.getServletContext();
        try {
            subject.logout();
        }catch (SessionException e){
            e.printStackTrace();
        }
//        issueRedirect(request,response,redirectUrl);
        SpringContextUtil.write(servletResponse,"登出成功",200);
        // 是否向下进行走 过滤器
        return false;
    }
}
