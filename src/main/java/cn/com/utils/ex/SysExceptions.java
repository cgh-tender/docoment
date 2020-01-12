package cn.com.utils.ex;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@ControllerAdvice
@ResponseBody
public class SysExceptions extends BaseExceptions {
    //其他错误
    @ExceptionHandler({Exception.class})
    public void exception(Exception ex) {
        resultFormat(1000, ex);
    }

    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    public void runtimeExceptionHandler(RuntimeException ex) {
        ex.printStackTrace();
        resultFormat(1, "运行时异常");
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    public void nullPointerExceptionHandler(NullPointerException ex) {
        ex.printStackTrace();
        resultFormat(2, "空指针异常");
    }

    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    public void classCastExceptionHandler(ClassCastException ex) {
        ex.printStackTrace();
        resultFormat(3, "类型转换异常");
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    public void iOExceptionHandler(IOException ex) {
        ex.printStackTrace();
        resultFormat(4, "IO异常");
    }

    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
    public void noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        ex.printStackTrace();
        resultFormat(5, "未知方法异常");
    }

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public void indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        resultFormat(6, "数组越界异常");
    }

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public void requestNotReadable(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        resultFormat(7, "400错误");
    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    public void requestTypeMismatch(TypeMismatchException ex) {
        ex.printStackTrace();
        resultFormat(8, "400错误");
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public void requestMissingServletRequest(MissingServletRequestParameterException ex) {
        ex.printStackTrace();
        resultFormat(9, "400错误");
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public void request405(HttpRequestMethodNotSupportedException ex) {
        ex.printStackTrace();
        resultFormat(10, "405错误");
    }

    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public void request406(HttpMediaTypeNotAcceptableException ex) {
        ex.printStackTrace();
        resultFormat(11, "406错误");
    }

    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    public void server500(RuntimeException ex) {
        ex.printStackTrace();
        resultFormat(12, "500错误");
    }

    //栈溢出
    @ExceptionHandler({StackOverflowError.class})
    public void requestStackOverflow(StackOverflowError ex) {
        ex.printStackTrace();
        resultFormat(13, "栈溢出");
    }

    //除数不能为0
    @ExceptionHandler({ArithmeticException.class})
    public void arithmeticException(ArithmeticException ex) {
        ex.printStackTrace();
        resultFormat(14, "除数不能为0");
    }

}
