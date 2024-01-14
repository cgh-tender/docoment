package cn.com.cgh.gateway.error;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalErrorWebException implements ErrorWebExceptionHandler {
    private static final JSONObject ERROR_CONVERTERS = new JSONObject();
    static {
        ERROR_CONVERTERS.fluentPut(String.valueOf(HttpStatus.NOT_FOUND.value()), "当前服务不可用请联系管理员");
        ERROR_CONVERTERS.fluentPut(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), "当前服务不可用请联系管理员");
    }
    /**
     * 这个Java函数是一个重写父类方法的函数，
     * 返回一个Mono<Void>类型的对象。它接受两个参数，一个是ServerWebExchange对象，
     * 另一个是Throwable对象。函数的目的是处理服务器网络交换和异常。
     * 返回的Mono<Void>对象为空。
     *
     * @param exchange
     * @param ex
     * @return
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        HttpStatusCode statusCode = HttpStatus.OK;
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(HttpStatus.OK);
            statusCode = ((ResponseStatusException) ex).getStatusCode();
        }
        return response.writeWith(Mono.just(response.bufferFactory().wrap(
                JSONObject.toJSONString(new JSONObject()
                        .fluentPut("code", "1")
                        .fluentPut("statusCode", Objects.requireNonNull(statusCode).value())
                        .fluentPut("message",
                                Optional.ofNullable(
                                        ERROR_CONVERTERS.get(String.valueOf(Objects.requireNonNull(statusCode).value()))
                                        )
                                .orElse(ex.getMessage())
                        ), JSONWriter.Feature.PrettyFormat
                ).getBytes(StandardCharsets.UTF_8)
        )));
    }
}
