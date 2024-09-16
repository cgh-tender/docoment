package cn.com.cgh.auth.handler;

import cn.com.cgh.romantic.util.ResponseImpl;
import com.alibaba.fastjson2.JSON;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * @author cgh
 */
@Component
public class TokenAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        ServerHttpResponse response = exchange.getResponse();
        // 401 未授权
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.just(
                response.bufferFactory().wrap(
                        JSON.toJSONBytes(
                                ResponseImpl.full("暂未登录，请您先进行登录").setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value())).setMessage("暂未登录，请您先进行登录"))
                )
        ));
    }
}
