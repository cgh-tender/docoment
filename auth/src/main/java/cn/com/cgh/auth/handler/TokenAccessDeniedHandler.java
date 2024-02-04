package cn.com.cgh.auth.handler;

import com.alibaba.fastjson2.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cgh
 */
@Component
public class TokenAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {
        ServerHttpResponse response = exchange.getResponse();
        // 403 拒绝访问
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.FORBIDDEN.value());
        map.put("message", "无访问权限");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JSON.toJSONBytes(map))));
    }
}
