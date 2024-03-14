package cn.com.cgh.gateway.error;

import cn.com.cgh.romantic.advice.GlobalErrorWebException;
import cn.com.cgh.romantic.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
@Component
@Order(-2)
@Slf4j
public class MySentinelRequestHandler implements WebExceptionHandler {
    @Autowired
    private GlobalErrorWebException globalErrorWebException;

    @Override
    public @NotNull Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.info("MySentinelRequestHandler");
        ServerHttpResponse response = exchange.getResponse();
        return Mono.just(ex).map(e -> {
            String message = ex.getMessage();
            if (e instanceof NotFoundException) {
                message = "30503";
            }
            return globalErrorWebException.parser(message, response, ex);
        }).flatMap((builder) ->
                ResponseUtil.writeResponseAsApplicationJson(response, builder)
        ).doOnError((e) -> log.info(e.getMessage()));
    }

    public MySentinelRequestHandler() {
    }
}
