package cn.com.filter.token;

import cn.com.filter.token.Body.TokenPayloadAbs;

public interface TokenBuilder {
    String getType();

    /**
     * 加载出类对象实例
     * @param t
     */
    TokenService builder(TokenPayloadAbs t);

    /**
     * 加载出类对象实例
     * @param token
     */
    TokenVerifyService builder(String token);
}
