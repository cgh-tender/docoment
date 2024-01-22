package cn.com.cgh.core.config;

import cn.com.cgh.gallery.util.ResponseImpl;
import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
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
    @SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class)
    public RestTemplate restTemplate() {
        return restTemplateBuilder().build();
    }
    static class ExceptionUtil {
        public static ClientHttpResponse handleException(HttpRequest request, byte[] body, ClientHttpRequestExecution execution, BlockException exception) {
            log.info(execution.toString());
            log.info(exception.toString());
            ResponseImpl.builder().message("restTemplate操作异常").build().FULL();
            return null;
        }
    }

}
