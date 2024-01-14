package cn.com.cgh.auth.exception;

import org.springframework.security.core.AuthenticationException;

public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException(String msg) {
        super(msg);
    }

    public VerificationCodeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
