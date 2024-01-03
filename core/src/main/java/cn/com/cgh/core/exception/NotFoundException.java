package cn.com.cgh.core.exception;

import com.feiniaojin.gracefulresponse.AbstractExceptionAliasRegisterConfig;
import com.feiniaojin.gracefulresponse.api.ExceptionAliasFor;
import org.apache.http.HttpStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Service;

@ConditionalOnClass(value = {AbstractExceptionAliasRegisterConfig.class})
@Service
@ExceptionAliasFor(code = HttpStatus.SC_NOT_FOUND + "", msg = "找不到方法", aliasFor = {})
public class NotFoundException extends CoreException {
}