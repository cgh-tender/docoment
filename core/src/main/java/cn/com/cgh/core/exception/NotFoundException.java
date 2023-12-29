package cn.com.cgh.core.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionAliasFor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.NoHandlerFoundException;

@Service
@ExceptionAliasFor(code = HttpStatus.SC_NOT_FOUND + "", msg = "找不到方法", aliasFor = NoHandlerFoundException.class)
public class NotFoundException extends CoreException {
}