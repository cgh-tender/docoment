package cn.com.demo;

import cn.com.entity.AuthHashAlgorithmName;
import lombok.NonNull;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class test {
    public static final String SALT = "chenguohai";
    public static final String password = "1";
    public static final AuthHashAlgorithmName hashAlgorithmName = AuthHashAlgorithmName.MD2;
    public static final int hashIterations = 2;
    public static void main(String[] args) {
        SimpleHash result = new SimpleHash("MD5", password, ByteSource.Util.bytes(SALT), hashIterations);
        System.out.println(result.toString());
        SimpleHash result1 = new SimpleHash("MD2", password, ByteSource.Util.bytes(SALT), hashIterations);
        System.out.println(result1.toString());
    }
}
