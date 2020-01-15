package cn.com.filter.token.Key;

import java.security.*;

public class MyPairKey implements KeyFactory {
    private final int KEYSIZE = 2048;

    @Override
    public Key getPublicKey(String key) {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(KEYSIZE, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        return keyPair.getPublic();
    }

    @Override
    public Key getPrivate(String key) {
        return null;
    }
}
