package cn.com.cgh.romantic.config.insterfaced;

import java.lang.annotation.*;

import static cn.com.cgh.romantic.constant.RomanticConstant.MASTER;

/**
 * @author cgh
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Ds {
    /**
     * 切换数据源名称
     */
    public String value() default MASTER;
}
