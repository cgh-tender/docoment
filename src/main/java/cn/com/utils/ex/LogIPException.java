package cn.com.utils.ex;

public class LogIPException extends RuntimeException{
    public LogIPException() {
    }

    public LogIPException(String message) {
        super(message);
    }

    public LogIPException(Throwable cause) {
        super(cause);
    }

    public LogIPException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogIPException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}