package cn.com.cgh.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class RestTemplateConfig {
    static {
        log.info("RestTemplateConfig:已启动");
    }
    @LoadBalanced
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }
    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder().build();
    }
}
