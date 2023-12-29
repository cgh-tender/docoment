package cn.com.cgh.core.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * 服务异常
 */
@ExceptionMapper(code = "1404", msg = "找不到对象")
public class Error1404Exception extends RuntimeException {
    public Error1404Exception() {

    }

    public Error1404Exception(String message) {
        super(message);
    }
}
