package cn.com.cgh.romantic.config.aspect.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParam {
    Class<?> objectType() default JsonParam.class;

    String value() default "";
}
