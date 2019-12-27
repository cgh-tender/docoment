package cn.com.utils.interfaceRun;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface YmlPropertySourceProperties {
    @AliasFor("prefix")
    String value() default "";

    @AliasFor("value")
    String prefix() default "";
}
