package cn.com.cgh.auth.handler;

import cn.com.cgh.romantic.util.ResponseImpl;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
@Slf4j
@Component
public class TokenLogoutSuccessHandler implements ServerLogoutSuccessHandler {
    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        ServerWebExchange exchange1 = exchange.getExchange();
        ServerHttpResponse response = exchange1.getResponse();
        HttpHeaders httpHeaders = response.getHeaders();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(
                JSON.toJSONBytes(ResponseImpl.full("退出成功"))
        )));
    }
}
