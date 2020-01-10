package cn.com.filter.shiro.filter;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class MyHash  extends SimpleHash {
    public static final String ALGORITHM_NAME = "MY";

    public MyHash() {
        super("MY");
    }

    public MyHash(Object source) {
        super("MY", source);
    }

    public MyHash(Object source, Object salt) {
        super("MY", source, salt);
    }

    public MyHash(Object source, Object salt, int hashIterations) {
        super("MY", source, salt, hashIterations);
    }

    public static Md5Hash fromHexString(String hex) {
        Md5Hash hash = new Md5Hash();
        hash.setBytes(Hex.decode(hex));
        return hash;
    }

    public static Md5Hash fromBase64String(String base64) {
        Md5Hash hash = new Md5Hash();
        hash.setBytes(Base64.decode(base64));
        return hash;
    }
}