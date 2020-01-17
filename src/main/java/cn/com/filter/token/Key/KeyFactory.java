package cn.com.filter.token.Key;

import cn.com.filter.token.Body.TokenPayloadAbs;
import io.jsonwebtoken.JwtBuilder;

import java.io.Serializable;
import java.security.Key;

public interface KeyFactory extends Serializable {
    TokenPayloadAbs decodeToken(String token);
    JwtBuilder builder();
    Key getPublicKey(String key);
    Key getPrivate(String key);
}
