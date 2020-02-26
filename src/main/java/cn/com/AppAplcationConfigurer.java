package cn.com;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.Filter;

/**
 * @author Haidar
 * @date 2020/1/7 18:07
 **/
@Configuration
public class AppAplcationConfigurer extends WebMvcConfigurationSupport {

    /**
     * <p> 增加静态目录
     * @param
     * @return
     * @author Haidar
     * @date 2020/1/7 18:41
     **/
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("classpath:/images/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SysCostInterceptor()).addPathPatterns("/**");
    }

    //    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
//        bean.setFilter(new AuthFilter());
//        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

}
