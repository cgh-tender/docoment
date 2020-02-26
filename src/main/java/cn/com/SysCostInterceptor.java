package cn.com;

import cn.com.utils.AuthFilterItemProperties;
import lombok.extern.log4j.Log4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j
public class SysCostInterceptor implements HandlerInterceptor {
    long start = System.currentTimeMillis();

    static {
        AuthFilterItemProperties authFilterItemProperties = SpringContextUtil.getBean(AuthFilterItemProperties.class);
        log.info("系统以 [" + SpringContextUtil.hashAlgorithmName.getName()+"] 加密方式进行验证,Shiro的验证方式为 [" + authFilterItemProperties.getIsSeparation() +" - "+ authFilterItemProperties.getIsSeparationDesc() + "] ");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        start = System.currentTimeMillis();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 取得根目录所对应的绝对路径
        String currentURL = request.getRequestURI();

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
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SpringContextUtil.ModeLog(request);
        long time = (System.currentTimeMillis() - start)/1000;
        log.info("[time] + " + time + "s");
    }
}
