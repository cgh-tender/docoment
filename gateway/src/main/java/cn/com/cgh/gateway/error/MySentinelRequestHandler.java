package cn.com.cgh.gateway.error;

import cn.com.cgh.romantic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author cgh
 */
@Component
@Order(-2)
@Slf4j
public class MySentinelRequestHandler implements WebExceptionHandler {
    @Override
    public @NotNull Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("sentinel请求异常 {}", ex.getMessage());
        throw new ServiceException(Objects.requireNonNull(exchange.getResponse().getStatusCode()).value());
    }

    public MySentinelRequestHandler() {
    }
}
