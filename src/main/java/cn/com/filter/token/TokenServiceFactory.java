package cn.com.filter.token;

public interface TokenServiceFactory {
    /**
     * 获取类对象
     */
    TokenBuilder getInstance();
}
