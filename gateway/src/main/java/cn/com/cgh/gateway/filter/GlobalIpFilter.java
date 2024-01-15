package cn.com.cgh.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalIpFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                //将获取的真实ip存入header微服务方便获取
                .header("X-Real-IP", exchange.getRequest().getRemoteAddress().getHostString())
//                .header("Content-Type","application/json;charset=UTF-8")
                .build();
        log.info("GlobalIpFilter: " + exchange.getRequest().getRemoteAddress().getHostString());
        return chain.filter(exchange.mutate().request(request).build());
    }

}
