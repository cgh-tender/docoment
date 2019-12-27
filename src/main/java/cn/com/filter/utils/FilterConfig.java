package cn.com.filter.utils;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

import javax.servlet.Filter;

//@Configuration
public class FilterConfig {
//    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AuthFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }
}
