package cn.com.cgh.auth.handler;

import cn.com.cgh.romantic.util.ResponseImpl;
import com.alibaba.fastjson2.JSON;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
@Component
public class TokenAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        return Mono.just(exchange).flatMap(e -> {
            ServerHttpResponse response = e.getResponse();
            // 403 拒绝访问
            response.setStatusCode(HttpStatus.FORBIDDEN);
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return response.writeWith(Mono.just(
                    response.bufferFactory().wrap(
                            JSON.toJSONBytes(ResponseImpl.full("无访问权限").setCode(String.valueOf(HttpStatus.FORBIDDEN.value()))
                                    .setMessage("无访问权限"))
                    ))
            );
        }).then();
    }
}
