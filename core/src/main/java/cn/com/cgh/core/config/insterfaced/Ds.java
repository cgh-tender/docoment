package cn.com.cgh.core.config.insterfaced;

import cn.com.cgh.core.util.Constants;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Ds {
    /**
     * 切换数据源名称
     */
    public String value() default Constants.MASTER;
}
