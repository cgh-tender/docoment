package cn.com.cgh.core.util;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Getter
public class CoreApplication implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CoreApplication.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return CoreApplication.applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return CoreApplication.applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return CoreApplication.applicationContext.getBean(name, clazz);
    }

    public static ApplicationContext getApplicationContext() {
        return CoreApplication.applicationContext;
    }}
