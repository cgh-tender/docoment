package cn.com.filter.utils;

import org.springframework.context.ConfigurableApplicationContext;

public class SpringContextUtil {
    private static ConfigurableApplicationContext applicationContext;

    //获取上下文
    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //设置上下文
    public static void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    //通过类型获取上下文中的bean
    public static Object getBean(Class<?> requiredType){
        return applicationContext.getBean(requiredType);
    }

}
