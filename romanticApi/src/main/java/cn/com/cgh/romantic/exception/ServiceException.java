package cn.com.cgh.romantic.exception;

/**
 * @author cgh
 */
public class ServiceException extends RuntimeException {
    public ServiceException(int message) {
        this(message + "");
    }

    public ServiceException(String message) {
        super(message);
    }
}
