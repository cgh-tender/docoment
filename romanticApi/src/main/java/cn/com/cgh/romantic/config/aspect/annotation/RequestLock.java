package cn.com.cgh.romantic.config.aspect.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author cgh
 * 配合 RequestKeyParam 使用 </br>
 * 标记方法或类，用于标记方法或类是否需要分布式锁 </br>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestLock {
    /**
     * redis request lock key
     */
    String prefix() default "RRL";
    /**
     * 参数分隔符号
     */
    String delimiter() default ":";

    /**
     * 数值为 3
     */
    int expire() default 3;

    /**
     * expire 的单位 默认秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
