package cn.com.filter.token.Key;

import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class MySigningKey implements KeyFactory {
    @Override
    public Key getPublicKey(String key) {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    @Override
    public Key getPrivate(String key) {
        return null;
    }
}
