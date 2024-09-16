package cn.com.cgh.romantic.advice;

import cn.com.cgh.romantic.util.RequestUtil;
import cn.com.cgh.romantic.util.ResponseImpl;
import cn.com.cgh.romantic.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.sql.SQLException;

/**
 * @author cgh
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private GlobalErrorWebException globalErrorWebException;
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * sql异常
     */
    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public Mono<Void> sqlException(ServerWebExchange rsp, SQLException ex) {
        LOGGER.error("!!! request uri:{} from {} server exception:{}", rsp.getRequest().getURI().getPath(), RequestUtil.getIpAddr(rsp), ex == null ? null : ex);
        LOGGER.error("code {}", rsp.getResponse().getStatusCode().value());
        ServerHttpResponse response = rsp.getResponse();
        assert ex != null;
        return globalErrorWebException.parser(ex.getMessage(), response, ex).flatMap((builder) ->
                ResponseUtil.writeResponseAsApplicationJson(response, builder)
        ).doOnError((e) -> LOGGER.info(e.getMessage()));
    }


    /**
     * 500错误.
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Mono<Void> serverError(ServerWebExchange rsp, Exception ex) {
        LOGGER.error("!!! request uri:{} from {} server exception:{}", rsp.getRequest().getURI().getPath(), RequestUtil.getIpAddr(rsp), ex == null ? null : ex);
        LOGGER.error("code {}", rsp.getResponse().getStatusCode().value());
        ServerHttpResponse response = rsp.getResponse();
        assert ex != null;
        return globalErrorWebException.parser(ex.getMessage(), response, ex).flatMap((builder) ->
                ResponseUtil.writeResponseAsApplicationJson(response, builder)
        ).doOnError((e) -> LOGGER.info(e.getMessage()));
    }


    //参数类型不匹配
    //getPropertyName()获取数据类型不匹配参数名称
    //getRequiredType()实际要求客户端传递的数据类型
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public Mono<ResponseImpl> requestTypeMismatch(TypeMismatchException ex) {
        LOGGER.error("参数类型有误:{}", ex.getMessage());
        return Mono.just(ResponseImpl.full("参数类型不匹配,参数" + ex.getPropertyName() + "类型应该为" + ex.getRequiredType()).setCode("99999"));
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public Mono<ResponseImpl> fileSizeLimit(MultipartException ex) {
        LOGGER.error("认证有误:{}", ex.getMessage());
        return Mono.just(ResponseImpl.full(ex.getMessage()).setCode("99999"));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public Mono<ResponseImpl> authenticationMethod(AuthenticationException m) {
        LOGGER.error("超过文件上传大小限制");
        if (m.getCause() != null) {
            LOGGER.error("超过文件上传大小限制:" + m.getCause().getMessage());
        }
        return Mono.just(ResponseImpl.full("超过文件大小限制,最大10MB").setCode("99999"));
    }

}
