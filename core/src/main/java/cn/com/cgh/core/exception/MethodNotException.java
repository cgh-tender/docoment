package cn.com.cgh.core.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionAliasFor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Service
@ExceptionAliasFor(code = HttpStatus.SC_METHOD_NOT_ALLOWED + "", msg = "当前请求方式不支持", aliasFor = HttpRequestMethodNotSupportedException.class)
public class MethodNotException extends CoreException {
}
