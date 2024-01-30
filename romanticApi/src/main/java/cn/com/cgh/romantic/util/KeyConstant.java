package cn.com.cgh.romantic.util;

import cn.hutool.core.codec.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class KeyConstant {

    public static final String CONTENT_INSTANCE = "RSA";

    public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl6zYR942HYzin4aS3w7zKkUq963iPqL2uwguktsMZAQ59ivAPTnlDgPcd+Ln+7/UkoclcaL68xBlyH7RMIGmVAESZXqSV+M9vcXKf84ZY0KQaONKBg1Mda/EsvhW6HNu5Qgc0hA6Ty6YF7XgxKghepTKDAI0FvynRR8p8aZUOU29Ny7RBu9TRDAJpLZLi85jRO1B90E20JvYawx7f9F2tidjfzRnwpdQpuzUbIQAf+QEYdWeoRRbL7mFvTYDpbIQoR2cL3fsDLOCW+GKfPMfllEPKwGPJmc9ipf/AxuvCHaUuAaX6PUBYW/t7QIYMK0gC8g3KuPoYdwrKAGemZ19RQIDAQAB";
    public static final String PRIVATE_KEY = "MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCXrNhH3jYdjOKfhpLfDvMqRSr3reI+ova7CC6S2wxkBDn2K8A9OeUOA9x34uf7v9SShyVxovrzEGXIftEwgaZUARJlepJX4z29xcp/zhljQpBo40oGDUx1r8Sy+Fboc27lCBzSEDpPLpgXteDEqCF6lMoMAjQW/KdFHynxplQ5Tb03LtEG71NEMAmktkuLzmNE7UH3QTbQm9hrDHt/0Xa2J2N/NGfCl1Cm7NRshAB/5ARh1Z6hFFsvuYW9NgOlshChHZwvd+wMs4Jb4Yp88x+WUQ8rAY8mZz2Kl/8DG68IdpS4Bpfo9QFhb+3tAhgwrSALyDcq4+hh3CsoAZ6ZnX1FAgMBAAECgf8BpaaKpl8ZB4g7R7rVsUSCF2cnugh4XfrAklEN4M3013MRXSCVdSUW/MCL/WCRxtBP0Uab8C5EVrsqoEpn0OwhT1JPVyk/rFvlroz6KD2tsmyhmDyrTwkFhdPA1hqB5pYLJBrFndgi+b/WBzLz5YNY1ox53JaBBY9GVLlDuHES+wNoAPs5AhCWJlLnIdzadzDQW7VIy1frR+Qet0gPd4IlCUDLVITVe8X5hvNsUYpFUtLrKLiNGc0G8uty+V8SETRrOFS+vz9HtmcfYd9xZpYyKN9UU1UzdV0xLhW3/oMyTlgtR9Xuj23JjyFYSQiV+/riHNRRwUkvuRqMJZYiMOECgYEA0bPUtOr1DVDzH5q5fTLIxIeDlZkTyhrxUgf0UkoF254KZY2lRgZLfvm5TNprX/5mGZD3JW2aHfx6Klh/s5g4YdBh2/WmW9O1yzLvBPmVbT/slj1HoZ8kQ+f58oqXvMiDHuzFYntGB3QOfGoBnUzv0rKcqQbBsskRqGMpi1qUGvUCgYEAuSle706uEuFT5wpYYSo2+QgaSoSgvIOMHy0JSrJEWZMXq/MZ5JVmxsqfrU0WfKfU94M+SjcBaoNht6lsmUpiK39BSeGXVnejMwHMRJDaCwwjPwBWRVR/SbLR5ejzQkz6vVpxx53xNgDoHG1P0kO1ZJh3vsP1hoAGWx/2GpP/BxECgYA3iPRv5Tl4iVOZrxQof+aDhY/ncl63S2NVYh3mHQxoGPaP5m86mDguLFxSahW9mlXXt3h8RQZnopGH3WAUY9+qp9ECu4+PCgY/BgIewyo7gI5bS2dI+ihUg37CKMn+iOuQqQutBRCKsMWVVU2QJuvsVLkAWnW5r14pxfkemqJWcQKBgBiRnL80OXTX4UIu5Ts/VrwEjziEEK/y5IzedPUsYlVgZyuxviXOjSyjhjyAlhNEUcXl7EX8tntjo3uzR22vqQcMg6VCAcq/ZTBHdMtETSpaGfMqIEhixeEJkUBogJ6ziDHH5oEOPAtmY2YiOw0zXsZSVNFDPzjxk+ZTf1xXdueBAoGBAINnXr1ylhRxztRmKyZDxWx2W3XxG1dsvOaL6DCvXwi4IVEjZgvLf0P2uYkZEl8cH2UAlCoKcuc2U3k4/wFD6gCS7CJQVzi2xHx5MPxr2O/+YEM81aJtb+4h75/uzfNKCBXPxl2PoLxpwvpkUWMsjdyfuPgfn2pvn5uNwYF6bqKD";

    public static void main1(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(CONTENT_INSTANCE);
        keyPairGenerator.initialize(2048);
        // 生成一对公私钥
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String privateKeyStr = Base64.encode(privateKey.getEncoded());
        String publicKeyStr = Base64.encode(publicKey.getEncoded());
        System.out.println(privateKeyStr);
        System.out.println(publicKeyStr);
    }
}
