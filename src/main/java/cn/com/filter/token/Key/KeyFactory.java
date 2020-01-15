package cn.com.filter.token.Key;

import java.io.Serializable;
import java.security.Key;

public interface KeyFactory extends Serializable {
    Key getPublicKey(String key);
    Key getPrivate(String key);
}
