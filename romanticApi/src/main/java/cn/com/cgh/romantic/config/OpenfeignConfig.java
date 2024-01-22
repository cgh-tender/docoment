package cn.com.cgh.romantic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:bootstrap.properties")
@EnableFeignClients(basePackages = {"cn.com.cgh.romantic"})
@Slf4j
public class OpenfeignConfig {
    static {
        log.info("OpenfeignConfig:已启动");
    }

    @Bean
    public AuthRequestInterceptor authRequestInterceptor(){
        return new AuthRequestInterceptor();
    }
}


