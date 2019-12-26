package cn.com.demo;

public class FileReadWrit {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long maxMemory = runtime.maxMemory();
        System.out.print(totalMemory);
        System.out.print(maxMemory);
    }
}
