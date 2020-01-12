package cn.com.utils.ex;

public class AppConfigException extends RuntimeException{
    public AppConfigException() {
    }

    public AppConfigException(String message) {
        super(message);
    }

    public AppConfigException(Throwable cause) {
        super(cause);
    }

    public AppConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}