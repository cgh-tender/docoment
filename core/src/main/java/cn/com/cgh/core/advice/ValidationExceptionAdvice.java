package cn.com.cgh.core.advice;

import cn.com.cgh.gallery.util.ResponseImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(100)
public class ValidationExceptionAdvice {

    @ExceptionHandler(value = {BindException.class, ValidationException.class})
    @ResponseBody
    public ResponseImpl exceptionHandler(Exception e) throws Exception {

        if (e instanceof BindException) {
            return this.handleBindException((BindException) e);
        }

        if (e instanceof ConstraintViolationException) {
            return this.handleConstraintViolationException(e);
        }

        return ResponseImpl.builder().build().FULL();
    }

    //Controller方法的参数校验码
    //Controller方法>Controller类>DTO入参属性>DTO入参类>配置文件默认参数码>默认错误码
    private ResponseImpl handleBindException(BindException e) throws Exception {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String msg = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));


        //属性校验上的注解，只会取第一个属性上的注解，因此要配置
        //hibernate.validator.fail_fast=true
        List<FieldError> fieldErrors = e.getFieldErrors();
        if (!CollectionUtils.isEmpty(fieldErrors)) {
            FieldError fieldError = fieldErrors.get(0);
            String fieldName = fieldError.getField();
            Object target = e.getTarget();
            Field field = null;
            Class<?> clazz = null;
            Object obj = target;
            if (fieldName.contains(".")) {
                String[] strings = fieldName.split("\\.");
                for (String fName : strings) {
                    clazz = obj.getClass();
                    field = obj.getClass().getDeclaredField(fName);
                    field.setAccessible(true);
                    obj = field.get(obj);
                }
            } else {
                clazz = target.getClass();
                field = target.getClass().getDeclaredField(fieldName);
            }
        }
        return  ResponseImpl.builder().message(msg).build().FULL();
    }

    private ResponseImpl handleConstraintViolationException(Exception e) throws Exception {
        ConstraintViolationException exception = (ConstraintViolationException) e;
        Set<ConstraintViolation<?>> violationSet = exception.getConstraintViolations();
        String msg = violationSet.stream().map(s -> s.getConstraintDescriptor().getMessageTemplate()).collect(Collectors.joining(";"));
        return ResponseImpl.builder().message(msg).build().FULL();
    }

}
