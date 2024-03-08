package cn.com.cgh.romantic.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author cgh
 */
@Slf4j
public class AuthRequestInterceptor implements RequestInterceptor {
    public AuthRequestInterceptor() {
    }

    @Override
    public void apply(RequestTemplate template) {
        String url = template.feignTarget().url();
        String path = template.path();
        // 获取请求对象
        log.info("feign拦截器 {} {}", url, path);
    }
}
