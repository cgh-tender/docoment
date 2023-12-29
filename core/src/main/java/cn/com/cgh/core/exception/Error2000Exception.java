package cn.com.cgh.core.exception;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * 登录异常
 */
@ExceptionMapper(code = "2000", msg = "请重新登录")
public class Error2000Exception extends RuntimeException {
    public Error2000Exception() {

    }

    public Error2000Exception(String message) {
        super(message);
    }
}
