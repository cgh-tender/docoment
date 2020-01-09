package cn.com.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExceptionResult<T> implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    private int code;   //返回码 非0即失败
    private String msg; //消息提示
    private T data; //返回的数据

    public ExceptionResult(){};

    public ExceptionResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static String success() {
        return success("成功");
    }

    public static<T> String success(String msg) {
        return JSON.toJSONString(new ExceptionResult(0, msg, null));
    }

    public static<T> String success(int code, String msg) {
        return JSON.toJSONString(new ExceptionResult(code, msg, null));
    }

    public static<T> String success(int code, String msg, T t) {
        return JSON.toJSONString(new ExceptionResult(code, msg, t));
    }

    public static String failed() {
        return failed("失败");
    }

    public static String failed(String msg) {
        return failed(-1, msg,null);
    }

    public static String failed(int code, String msg) {
        return failed(-1, msg,null);
    }

    public static<T> String failed(int code, String msg, T t) {
        return JSON.toJSONString(new ExceptionResult(code, msg, t));
    }
}
