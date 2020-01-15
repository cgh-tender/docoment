package cn.com.filter.token.Key;

import java.security.Key;

public class MyCustom implements KeyFactory {
    @Override
    public Key getPublicKey(String key) {
        return null;
    }

    @Override
    public Key getPrivate(String key) {
        return null;
    }
}
