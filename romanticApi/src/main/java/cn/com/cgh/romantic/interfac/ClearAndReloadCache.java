package cn.com.cgh.romantic.interfac;

import java.lang.annotation.*;

/**
 * @author cgh
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface ClearAndReloadCache {
    //普通的操作说明
    String name() default "";

    //spel表达式的操作说明
    String spelName() default "";
}
