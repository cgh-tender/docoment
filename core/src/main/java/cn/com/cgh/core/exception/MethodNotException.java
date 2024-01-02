package cn.com.cgh.core.exception;

import com.feiniaojin.gracefulresponse.AbstractExceptionAliasRegisterConfig;
import com.feiniaojin.gracefulresponse.api.ExceptionAliasFor;
import org.apache.http.HttpStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@ConditionalOnClass(value = {AbstractExceptionAliasRegisterConfig.class})
@Service
@ExceptionAliasFor(code = HttpStatus.SC_METHOD_NOT_ALLOWED + "", msg = "当前请求方式不支持", aliasFor = HttpRequestMethodNotSupportedException.class)
public class MethodNotException extends CoreException {
}
