package cn.com.cgh.ayth;

import java.text.MessageFormat;

public class TestMain {
    public static void main(String[] args) {
        String JWT_CACHE_KEY = "jwt:username:{0}:{1}";
        String format = MessageFormat.format(JWT_CACHE_KEY, "123", "456");
        System.out.println(format);
    }
}
