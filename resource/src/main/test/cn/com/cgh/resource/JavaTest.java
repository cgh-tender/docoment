package cn.com.cgh.resource;

import java.util.Optional;

public class JavaTest {
    public static void main(String[] args) {
        Boolean a = Optional.ofNullable("b").map(f -> f.equals("a")).orElse(false);
        System.out.println(a);
    }
}
