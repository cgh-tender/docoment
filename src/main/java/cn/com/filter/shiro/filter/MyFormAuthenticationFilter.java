package cn.com.filter.shiro.filter;

import cn.com.SpringContextUtil;
import cn.com.entity.RestStatus;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        try {
            boolean separation = SpringContextUtil.isSeparation(servletRequest);
            if (separation){
                if (!super.isAccessAllowed(request, response, mappedValue)){
                    SpringContextUtil.write(servletResponse,RestStatus.LOGOUT.getName(),RestStatus.LOGOUT.getCode());
                    log.info("MyFormAuthenticationFilter 登录验证未通过");
                    return false;
                }
            }else{
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
