package cn.com.cgh.gateway.error;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
@Slf4j
public class MySentinelRequestHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (HttpStatus.NOT_FOUND.equals(exchange.getResponse().getStatusCode())) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            String errorMessage = JSONUtil.toJsonStr(new ErrorResult("1","服务异常"));
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                    .bufferFactory().wrap(errorMessage.getBytes())));
        }
        return Mono.error(ex);
    }

    public MySentinelRequestHandler() {
    }

    public static class ErrorResult {
        private final String code;
        private final String msg;

        public ErrorResult(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return this.code;
        }

        public String getMessage() {
            return this.msg;
        }
    }
}
