package cn.com.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    private int code;   //返回码 非0即失败
    private boolean success;
    private String message; //消息提示
    private T data; //返回的数据

    private Result(){}

    public Result(int code,boolean success,String msg,T t){
        this.code = code;
        this.success = success;
        this.message = msg;
        this.data = t;
    }

    public static String success() {
        return success(true,"成功");
    }

    public static String success(int code,String msg){
        return success(code,true,msg);
    }

    public static String success(boolean success,String msg) {
        return success(200,success,msg);
    }

    public static String success(int code, boolean success,String msg) {
        return success(code,success,msg,null);
    }

    public static<T> String success(int code, boolean success,String msg, T t) {
        return JSON.toJSONString(new Result(code,success,msg,t));
    }

    public static String failed() {
        return failed(true,"失败");
    }

    public static String failed(int code,String msg){
        return failed(code,false,msg);
    }

    public static String failed(boolean success,String msg) {
        return failed(-1,success,msg);
    }

    public static String failed(int code, boolean success,String msg) {
        return failed(code,success,msg,null);
    }

    public static<T> String failed(int code, boolean success,String msg, T t) {
        return JSON.toJSONString(new Result(code,success,msg,t));
    }
}
