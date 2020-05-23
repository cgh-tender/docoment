package cn.com;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class mainTest {
    public static void main(String[] args) {
//        String url = "/Users/cgh/Downloads/2020-04-22/处理汇总报表下钻1.1（工单总量）_2020-04-22.xlsx";
//        File file = new File(url);
//        System.out.println(file.getName());
        String a = "a,b";
        String b = "";
        for(String  s : a.split(",")){
            b += "'" + s +"',";
        }
        System.out.println(b.substring(0,b.length()-1));
    }
}
