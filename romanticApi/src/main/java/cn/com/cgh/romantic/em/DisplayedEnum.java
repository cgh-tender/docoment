package cn.com.cgh.romantic.em;

import cn.com.cgh.romantic.pojo.SelectOption;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author cgh
 */
public interface DisplayedEnum extends Serializable, IEnum<Long> {
    String DEFAULT_VALUE_NAME = "value";
    String DEFAULT_LABEL_NAME = "label";

    @Override
    default Long getValue() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_VALUE_NAME);
        if (field == null) {
            return null;
        }
        try {
            field.setAccessible(true);
            return Long.valueOf(field.get(this).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonValue
    default String getLabel() {
        System.out.println("getLabel ... ");
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_LABEL_NAME);
        if (field == null) {
            return null;
        }
        try {
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    default List<SelectOption> getOptions(){
        return null;
    }
}
