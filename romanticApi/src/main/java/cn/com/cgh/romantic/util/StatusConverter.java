package cn.com.cgh.romantic.util;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.nacos.common.utils.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


/**
 * StatusConverter是一个转换器工厂类，用于将字符串转换为枚举类型。
 * 它实现了ConverterFactory接口，该接口规定了一个方法来创建特定类型的转换器。
 *
 * @author cgh
 */
@Component
public class StatusConverter implements ConverterFactory<String, Enum> {

    public static Class<?> getEnumType(Class<?> targetType) {
        Class<?> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
        return enumType;
    }

    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StatusConverter.LongToEnum(getEnumType(targetType));
    }


    private static class LongToEnum<T extends Enum> implements Converter<String, T> {


        private final Class<T> enumType;

        LongToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        @Nullable
        public T convert(String source) {
            if (source.isEmpty()) {
                // It's an empty enum identifier: reset the enum value to null.
                return null;
            }
            try {
                return (T) Enum.valueOf(this.enumType, source.trim());
            } catch (Exception e) {
                T[] enumConstants = this.enumType.getEnumConstants();
                if (enumConstants != null) {
                    List<T> value = Arrays.stream(enumConstants).filter(constant -> StringUtils.equals(String.valueOf(Optional.ofNullable(BeanUtil.getProperty(constant, "value")).get()), source))
                            .toList();
                    if (!value.isEmpty()) {
                        return value.get(0);
                    }
                }
                throw e;
            }
        }
    }

}
