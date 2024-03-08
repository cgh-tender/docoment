package cn.com.cgh.romantic.advice;

import cn.com.cgh.romantic.server.resource.IResourceErrorController;
import cn.com.cgh.romantic.util.ResponseImpl;
import cn.com.cgh.romantic.util.ResponseUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

/**
 * @author cgh
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GlobalErrorWebException implements ErrorWebExceptionHandler {
    @Autowired
    private IResourceErrorController iResourceErrorController;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final static Pattern REGEX = Pattern.compile("^[0-9]+$");
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
     */
    @Override
    public @NotNull Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        HttpStatusCode statusCode;
        if (ex instanceof ResponseStatusException) {
            response.setStatusCode(HttpStatus.OK);
            statusCode = ((ResponseStatusException) ex).getStatusCode();
        } else {
            statusCode = HttpStatus.OK;
        }
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
            String message = ex.getMessage();
            if (message != null && REGEX.matcher(message).find()){
                ResponseImpl<String> errorMessage = iResourceErrorController.getErrorMessage(Long.valueOf(ex.getMessage()));
                return errorMessage.getData() == null ? message : errorMessage.getData();
            }else {
                message = ERROR_CONVERTERS.get(String.valueOf(statusCode.value())) == null ? message : String.valueOf(ERROR_CONVERTERS.get(String.valueOf(statusCode.value())));
            }
            return message;
        }, threadPoolTaskExecutor)).flatMap((message) ->
                ResponseUtil.writeResponse(response, ResponseImpl.builder().message(String.valueOf(message)).build().full())
        ).doOnError((e)->{
            log.info(e.getMessage());
        });
    }
}
