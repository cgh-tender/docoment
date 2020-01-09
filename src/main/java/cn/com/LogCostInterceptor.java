package cn.com;

import lombok.extern.log4j.Log4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j
public class LogCostInterceptor implements HandlerInterceptor {
    long start = System.currentTimeMillis();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        start = System.currentTimeMillis();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String addr = request.getRemoteAddr();
        String token = request.getHeader("TOKEN");
        long time = (System.currentTimeMillis() - start)/1000;
//        log.info("当前请求 [ " + uri + " ] - 请求类型 [ " + method + " ] - 请求 IP [ " + addr + " ] - 请求用时 [ " + time + " ]");
        String s = "当前请求 [ %s ] - 请求类型 [ %s ] - 请求 IP [ %s ] - 请求用时 [ %ss ]";
        log.info(String.format(s, uri,method,addr,time));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {


    }
}
