package cn.com.cgh.romantic.exception;

/**
 * @author cgh
 */
public class RequestLockException extends RuntimeException{
    public RequestLockException(String message) {
        super(message);
    }
}
