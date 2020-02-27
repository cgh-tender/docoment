package cn.com.filter.shiro.filter;

import cn.com.SpringContextUtil;
import cn.com.entity.Result;
import cn.com.filter.token.Body.TokenPayloadAbs;
import cn.com.filter.token.TokenServiceFactory;
import cn.com.filter.token.TokenVerifyService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j
public class ShiroFilter extends AccessControlFilter {
    private TokenServiceFactory tokenServiceFactory = SpringContextUtil.getBean(TokenServiceFactory.class);
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        log.info("ShiroFilter onAccessDenied");
        Subject subject = getSubject(request, response);
        log.info(subject.isAuthenticated());
        boolean separation = SpringContextUtil.isSeparation((HttpServletResponse) response);
        if (separation){
            Object principal = subject.getPrincipal();
            log.info(principal);
            Session session = subject.getSession();
            log.info(session);
            return true;
        }else {
            String token = ((HttpServletRequest) request).getHeader("TOKEN");
            if (StringUtils.isBlank(token)){
                Result.failed((HttpServletResponse) response,200,"请重新登录");
            }
            TokenVerifyService builder = tokenServiceFactory.getInstance().builder(token);
            if (builder.SysIsOverdue()){
                subject.logout();
            }else {
                subject.login(builder.decodeToken());
                if (builder.isOverdue()){
                    log.info("重新加载Token");
                    String reToken = builder.reToken();
                    log.info(reToken);
                    tokenServiceFactory.getInstance().builder(reToken).saveToken();
                    ((HttpServletResponse) response).addHeader("TOKEN",reToken);
                    SpringContextUtil.write((HttpServletResponse) response,"false",10000);
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}
