package cn.com.utils.ex;

public class LogOutException extends RuntimeException{
    public LogOutException() {
    }

    public LogOutException(String message) {
        super(message);
    }

    public LogOutException(Throwable cause) {
        super(cause);
    }

    public LogOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}