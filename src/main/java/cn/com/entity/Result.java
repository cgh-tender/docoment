package cn.com.entity;

import cn.com.SpringContextUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    private int code;   //返回码 非0即失败
    private boolean success;
    private String message; //消息提示
    private T data; //返回的数据
    private String type;

    private Result(){}

    public Result(int code,boolean success,String msg,T t,String type){
        this.code = code;
        this.success = success;
        this.message = msg;
        this.data = t;
        this.type = type;
    }

    public static void success() {
        success(null,true,"成功");
    }

    public static void success( HttpServletResponse response) {
        success(response,true,"成功");
    }

    public static void success( String msg) {
        success(null,true,msg);
    }

    public static void success(HttpServletResponse response, String msg) {
        success(response,true,msg);
    }

    public static void success(boolean success,String msg) {
        success(null,200,success,msg);
    }

    public static void success( HttpServletResponse response,boolean success,String msg) {
        success(response,200,success,msg);
    }

    public static void success(int code, String msg) {
        success(null,code,true,msg,null);
    }

    public static void success(HttpServletResponse response,int code, String msg) {
        success(response,code,true,msg,null);
    }

    public static void success(int code, boolean success,String msg) {
        success(null,code,success,msg,null);
    }

    public static void success(HttpServletResponse response,int code, boolean success,String msg) {
        success(response,code,success,msg,null);
    }

    public static<T> void success(HttpServletResponse response, int code, boolean success,String msg, T t) {
        if (response != null){
            SpringContextUtil.write(response,JSON.toJSONString(new Result(code,success,msg,t,"info")));
        }
        SpringContextUtil.write(JSON.toJSONString(new Result(code,success,msg,t,"info")));
    }

    public static void failed() {
        failed(null,false,"失败");
    }

    public static void failed(HttpServletResponse response) {
        failed(response,false,"失败");
    }

    public static void failed(String msg) {
        failed(null,false,msg);
    }

    public static void failed(HttpServletResponse response,String msg) {
        failed(response,false,msg);
    }

    public static void failed(int code,String msg){
        failed(null,code,false,msg);
    }


    public static void failed(HttpServletResponse response,int code,String msg){
        failed(response,code,false,msg);
    }

    public static void failed(boolean success,String msg) {
        failed(null,-1,success,msg);
    }

    public static void failed(HttpServletResponse response, boolean success,String msg) {
        failed(response,-1,success,msg);
    }

    public static void failed(int code, boolean success,String msg) {
        failed(null, code,success,msg,null);
    }

    public static void failed(HttpServletResponse response, int code, boolean success,String msg) {
        failed(response, code,success,msg,null);
    }

    public static<T> void failed(HttpServletResponse response,int code, boolean success,String msg, T t) {
        if (response != null){
            SpringContextUtil.write(response,JSON.toJSONString(new Result(code,success,msg,t,"error")));
        }
        SpringContextUtil.write(JSON.toJSONString(new Result(code,success,msg,t,"error")));
    }
}
