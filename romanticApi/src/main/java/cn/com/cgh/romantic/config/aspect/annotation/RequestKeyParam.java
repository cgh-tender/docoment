package cn.com.cgh.romantic.config.aspect.annotation;
import java.lang.annotation.*;

/**
 * @author cgh
 * 标记需要做为key的参数
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestKeyParam {
}