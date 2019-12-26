package cn.com.filter.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.FilterConfig;
import java.io.IOException;

//@WebFilter(filterName = "authFilter",urlPatterns = "/*")
public class AuthFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(AuthFilter.class);
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("init Filter");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("s");
        filterChain.doFilter(servletRequest,servletResponse);
        return;
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }
}
