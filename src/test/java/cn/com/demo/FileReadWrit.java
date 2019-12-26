package cn.com.demo;

public class FileReadWrit {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long maxMemory = runtime.maxMemory();
        System.out.println(totalMemory);
        System.out.println(maxMemory);
    }
}
