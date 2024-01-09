package cn.com.cgh.gateway.error;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class MyBlockRequestHandler implements BlockRequestHandler {

    public MyBlockRequestHandler() {
    }

    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable ex) {
        log.info("ssssss");
        return this.acceptsHtml(exchange) ? this.htmlErrorResponse(ex) : ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.APPLICATION_JSON_UTF8).body(BodyInserters.fromObject(this.buildErrorResult(ex)));
    }

    private Mono<ServerResponse> htmlErrorResponse(Throwable ex) {
        return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS).contentType(MediaType.TEXT_PLAIN).syncBody("Blocked by Sentinel: " + ex.getClass().getSimpleName());
    }

    private MyBlockRequestHandler.ErrorResult buildErrorResult(Throwable ex) {
        return new MyBlockRequestHandler.ErrorResult(HttpStatus.TOO_MANY_REQUESTS.value()+"", "Blocked by Sentinel: " + ex.getClass().getSimpleName());
    }

    private boolean acceptsHtml(ServerWebExchange exchange) {
        try {
            List<MediaType> acceptedMediaTypes = exchange.getRequest().getHeaders().getAccept();
            acceptedMediaTypes.remove(MediaType.ALL);
            MediaType.sortBySpecificityAndQuality(acceptedMediaTypes);
            Stream<MediaType> var10000 = acceptedMediaTypes.stream();
            MediaType var10001 = MediaType.TEXT_HTML;
            var10001.getClass();
            return var10000.anyMatch(var10001::isCompatibleWith);
        } catch (InvalidMediaTypeException var3) {
            return false;
        }
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
