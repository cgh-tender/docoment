package cn.com.demo;


import java.io.*;
import java.nio.charset.Charset;

public class FileReadWrit {

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.totalMemory() / 1024 / 1024 + " M");
        System.out.println(runtime.maxMemory() / 1024 / 1024 + " M");

        FileWriter fw = null;
        InputStreamReader reader = null;
        try{
            fw = new FileWriter("C:\\Users\\HaiDar\\Desktop\\bbb.txt");
            char[] tempchars = new char[1000];
            reader = new InputStreamReader(new FileInputStream(new File("C:\\Users\\HaiDar\\Desktop\\aaa.txt")), Charset.forName("GBK"));
            int charread = 0;
            while ((charread = reader.read(tempchars)) != -1){
                for(int i = 0;i < charread;i++) {
                    if(tempchars[i] == '\r') {
                        continue;
                    }else {
                        fw.write(tempchars[i]);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fw != null) {
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
