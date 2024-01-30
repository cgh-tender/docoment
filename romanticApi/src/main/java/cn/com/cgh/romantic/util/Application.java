package cn.com.cgh.romantic.util;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Getter
public class Application implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Application.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return Application.applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return Application.applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return Application.applicationContext.getBean(name, clazz);
    }

    public static ApplicationContext getApplicationContext() {
        return Application.applicationContext;
    }}
