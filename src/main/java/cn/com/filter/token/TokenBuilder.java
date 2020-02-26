package cn.com.filter.token;

import cn.com.filter.token.Body.TokenPayloadAbs;

public interface TokenBuilder {
    String getType();
    TokenService builder(TokenPayloadAbs t);
    TokenVerifyService builder(String token);
}
