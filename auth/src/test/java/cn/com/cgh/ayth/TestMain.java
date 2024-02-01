package cn.com.cgh.ayth;

import java.util.regex.Pattern;

public class TestMain {
    public static void main(String[] args) {
        Pattern REGEX = Pattern.compile("^[0-9]*$");
        boolean b = REGEX.matcher("123445").find();
        System.out.println(b);
    }
}
