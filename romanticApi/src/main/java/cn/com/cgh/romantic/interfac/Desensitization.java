package cn.com.cgh.romantic.interfac;

import cn.com.cgh.romantic.config.DesensitizationSerialize;
import cn.com.cgh.romantic.em.DesensitizationEnum;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cgh
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizationSerialize.class)
public @interface Desensitization {
    /**
     * 脱敏数据类型，CUSTOM注解下，startInclude和endExclude生效
     */
    DesensitizationEnum type() default DesensitizationEnum.CUSTOM;
    /**
     * 脱敏开始位置（包含）
     */
    int startInclude() default 0;

    /**
     * 脱敏结束位置（不包含）
     */
    int endExclude() default 0;
}
