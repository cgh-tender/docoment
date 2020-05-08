package cn.com.filter.shiro.filter;

import cn.com.SpringContextUtil;
import cn.com.utils.ex.LogOutException;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        boolean separation = SpringContextUtil.isSeparation(servletResponse);
        log.info("MyFormAuthenticationFilter isAccessAllowed");
//        if (separation){
//            boolean b = super.isAccessAllowed(request, response, mappedValue);
//            Subject subject = this.getSubject(request, response);
//            subject.getSession();
//            log.info(b);
//            if (!b){
//                throw new LogOutException("请重新登录");
//            }
//        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest request1 = (HttpServletRequest)request;
        String token = request1.getHeader("TOKEN");
        if (token != null || "OPTIONS".equals(request1.getMethod())){
            return true;
        }
        return super.onAccessDenied(request, response);
    }

    public MyFormAuthenticationFilter() {
        super();
    }
}
