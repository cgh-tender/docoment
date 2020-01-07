package cn.com.utils.interfaceRun;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface YmlPropertySourceComponent {
    String name() default "ymlPropertySourceComponent";
    String[] value();
}
