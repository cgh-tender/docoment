package cn.com.cgh.romantic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

/**
 * @author cgh
 */
@Configuration
public class WebfluxAOPConfig {
    @Bean
    public WebFilter requestContextFilter() {
        return (exchange, chain) -> {
            RequestContextHolder.set(exchange);
            return chain.filter(exchange).doFinally(signalType -> RequestContextHolder.remove());
        };
    }

    // 请求上下文持有类
    public static class RequestContextHolder {
        private static final ThreadLocal<ServerWebExchange> EXCHANGE = new ThreadLocal<>();

        public static void set(ServerWebExchange exchange) {
            RequestContextHolder.EXCHANGE.set(exchange);
        }

        public static ServerWebExchange get() {
            return EXCHANGE.get();
        }

        public static void remove() {
            EXCHANGE.remove();
        }
    }
}
