package cn.com.filter.utils;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
@Log4j
@Order(2)
public class SpringContextUtil implements ApplicationContextAware {

    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext args0) throws BeansException {
        SpringContextUtil.applicationContext = args0;
    }

    public static Object getBean(@NonNull String name){
        return applicationContext.getBean(name);
    }

    //通过类型获取上下文中的bean
    public static Object getBean(@NonNull Class<?> requiredType){
        return applicationContext.getBean(requiredType);
    }

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    public static boolean isAjax(HttpServletRequest req) {
        String contentTypeHeader = req.getHeader("content-type");
        String acceptHeader = req.getHeader("accept");
        String xRequestedWith = req.getHeader("x-requested-wit");
        return (contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith);
    }

    public static boolean isAjax() {
        HttpServletRequest req = getRequest();
        String contentTypeHeader = req.getHeader("content-type");
        String acceptHeader = req.getHeader("accept");
        String xRequestedWith = req.getHeader("x-requested-wit");
        return (contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith);
    }
}
