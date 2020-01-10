package cn.com.entity;

import cn.com.SpringContextUtil;
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
        success(true,"成功");
    }

    public static void success(String msg) {
        success(true,msg);
    }

    public static void success(int code,String msg){
        success(code,true,msg);
    }

    public static void success(boolean success,String msg) {
        success(200,success,msg);
    }

    public static void success(int code, boolean success,String msg) {
        success(code,success,msg,null);
    }

    public static<T> void success(int code, boolean success,String msg, T t) {
        SpringContextUtil.write(JSON.toJSONString(new Result(code,success,msg,t,"info")));
    }

    public static void failed() {
        failed(false,"失败");
    }
    public static void failed(String msg) {
        failed(false,msg);
    }
    public static void failed(String msg,String type) {
        failed(false,msg);
    }
    public static void failed(int code,String msg){
        failed(code,false,msg);
    }

    public static void failed(boolean success,String msg) {
        failed(-1,success,msg);
    }

    public static void failed(int code, boolean success,String msg) {
        failed(code,success,msg,null);
    }

    public static<T> void failed(int code, boolean success,String msg, T t) {
        SpringContextUtil.write(JSON.toJSONString(new Result(code,success,msg,t,"error")));
    }
}
