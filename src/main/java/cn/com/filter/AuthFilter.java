package cn.com.filter;


import cn.com.SpringContextUtil;
import cn.com.utils.AuthFilterItemProperties;
import lombok.extern.log4j.Log4j;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "authFilter",urlPatterns = "/*")
@Log4j
public class AuthFilter implements Filter {

    private List<String>  items;

    @Override
    public void init(FilterConfig filterConfig) {
        AuthFilterItemProperties authFilterItemProperties = SpringContextUtil.getBean(AuthFilterItemProperties.class);
        log.info("系统以 [" + SpringContextUtil.hashAlgorithmName.getName()+"] 加密方式进行验证,Shiro的验证方式为 [" + authFilterItemProperties.getIsSeparation() +" - "+ authFilterItemProperties.getIsSeparationDesc() + "] ");
        log.info("init AuthFilter");
}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 取得根目录所对应的绝对路径
        String currentURL = request.getRequestURI();

        HttpSession session = request.getSession(false);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,requesttype,UUID,TOKEN,MENUCODE,separation");
        response.setHeader("Access-Control-Max-Age", "3628800");
        response.setHeader("Accept", String.valueOf(MediaType.APPLICATION_JSON));
        response.setHeader("Content-Type", "application/json;charset=UTF-8");

        // 禁止浏览器缓存所有动态页面
        response.setDateHeader("Expires", -10);
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        log.info(String.format(" [authFilter] - currentURL : %s ",currentURL));
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

    @Override
    public void destroy() {
        log.info("destroy");
    }
}
