package cn.com.cgh.romantic.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthRequestInterceptor implements RequestInterceptor {
    public AuthRequestInterceptor() {
    }

    @Override
    public void apply(RequestTemplate template) {
        log.info("feign拦截器");
        template.header("Authorization", "Bearer " + "123456");
    }
}
