package cn.com.utils.ex;

import cn.com.SpringContextUtil;
import cn.com.entity.Result;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Log4j
public class BaseExceptions {
    private static final String logExceptionFormat = "Capture Exception By BaseExceptions: Code: %s Detail: %s URI: %s";

    protected <R extends Throwable> void resultFormat(Integer code, R ex) {
        ex.printStackTrace();
        resultFormat(code,ex.getMessage());
    }
    protected void resultFormat(Integer code, String ex) {
        log.error(String.format(logExceptionFormat, code, ex, SpringContextUtil.getRequest().getRequestURI()));
        Result.failed(code, ex);
    }

}
