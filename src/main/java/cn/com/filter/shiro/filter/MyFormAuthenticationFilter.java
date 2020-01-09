package cn.com.filter.shiro.filter;

import lombok.extern.log4j.Log4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Log4j
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest request1 = (HttpServletRequest)request;
        String token = request1.getHeader("TOKEN");
        if (token != null || "OPTIONS".equals(request1.getMethod())){
            return true;
        }
        log.info("MyFormAuthenticationFilter");
        return super.onAccessDenied(request, response);
    }

    public MyFormAuthenticationFilter() {
        super();
    }
}
