package cn.com.application;

import cn.com.SimpleService.AServiceFactory;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringRunApplication {
    @Test
    public void test1(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("cn.com");
        AServiceFactory bean = applicationContext.getBean(AServiceFactory.class);
        bean.getInstance("oracle").create();
    }

}
