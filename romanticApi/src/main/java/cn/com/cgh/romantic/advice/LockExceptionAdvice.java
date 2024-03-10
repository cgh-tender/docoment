package cn.com.cgh.romantic.advice;

import cn.com.cgh.romantic.exception.RequestLockException;
import cn.com.cgh.romantic.util.ResponseImpl;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
@Order(100)
public class LockExceptionAdvice {

    /**
     * 处理RequestLockException异常的处理器。
     * 当遇到请求锁定异常时，返回一个包含异常信息的响应体。
     *
     * @param e RequestLockException 异常对象，包含具体的错误信息。
     * @return Mono<ResponseImpl> 返回一个响应流，其中包含关于错误的详细信息。
     */
    @ExceptionHandler(value = {RequestLockException.class})
    @ResponseBody
    public Mono<ResponseImpl<String>> exceptionHandler(RequestLockException e) {
        // 构建并返回一个包含异常消息的响应体
        return Mono.just(ResponseImpl.<String>builder().message(e.getMessage()).build().full());
    }
}