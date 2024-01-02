package cn.com.cgh.core.exception;

import com.feiniaojin.gracefulresponse.AbstractExceptionAliasRegisterConfig;
import com.feiniaojin.gracefulresponse.api.ExceptionMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

/**
 * 服务异常
 */
@ConditionalOnClass(value = {AbstractExceptionAliasRegisterConfig.class})
@ExceptionMapper(code = "1404", msg = "找不到对象")
public class Error1404Exception extends RuntimeException {
    public Error1404Exception() {

    }

    public Error1404Exception(String message) {
        super(message);
    }
}
