package cn.com.cgh.romantic.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.RetryableLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@EnableDiscoveryClient
public class WebClientConfig {
    static {
        log.info("WebClientConfig:已启动");
        log.info("RestTemplate:已禁用");
    }

    @LoadBalanced
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient() {
        return webClientBuilder().build();
    }

    @Bean
    @Conditional(DisableRestTemplateCondition.class)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

class DisableRestTemplateCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 返回false，表示不创建RestTemplate的bean
        return false;
    }
}