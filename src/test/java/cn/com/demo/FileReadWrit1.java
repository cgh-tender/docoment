package cn.com.demo;


import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileReadWrit1 {

    final static String inputUrl = "/Users/cgh/Desktop/aaa";
    public static void main(String[] args) {
        FileInputStream input = null;
        FileOutputStream output = null;
       try {
           //获取inputfile下的文件或文件夹。
           File path = new File(inputUrl);
           File[] list = path.listFiles();
           for(File file : list){
               //如果文件为空，删除。
               if(file.length()==0)file.delete();
               //如果文件为隐藏文件，删除。
               if(file.isHidden() && file.isFile())file.delete();
           }
           //清除完空文件和隐藏文件后，再次获取inputfile下的文件或文件夹。
           list = path.listFiles();
           for(File dir : list){
               //如果不是文件，跳过循环。
               if(!dir.isFile())continue;
               //
               File[] data = list;//dir.listFiles();
               if(data.length==0)continue;

               System.out.println(dir.getParent());
//                            System.out.println(dir.get);

               // System.out.println(dir.getAbsolutePath()+File.separator+dir.getName());
               File parent = new File(dir.getParent());
               System.out.println(parent.getAbsolutePath()+File.separator+parent.getName());
               output = new FileOutputStream(parent.getAbsolutePath()+File.separator+parent.getName());
//                            output = new FileOutputStream(dir.getAbsolutePath());

System.out.println(output.toString());
               for(File file : data){
                   if(file.isDirectory() || file.isHidden())continue;

                   System.out.println("read111111:"+parent.getAbsolutePath()+File.separator+parent.getName());
                   input = new FileInputStream(file);
                   IOUtils.copy(input, output);
                   input.close();
               }
               output.close();
//               System.out.println("input:"+parent.getAbsolutePath()+File.separator+parent.getName());
//               input = new FileInputStream(parent.getAbsolutePath()+File.separator+parent.getName());
           }
       } catch(Exception e){
           e.printStackTrace();
       }finally {
//                        ESBUtils.deleteDir(new File(inputfile));
           try {
               if(input!=null){
                   input.close();
               }
               if(output!=null) {
                   output.close();
               }
           }catch (Exception e){
               e.printStackTrace();
           }

       }

    }
}
