package cn.com.cgh.romantic.em;

import cn.hutool.core.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cgh
 */

@Getter
@AllArgsConstructor
public enum GenderStatus implements DisplayedEnum {
    /**
     * 正常
     */
    MALE(0L, "男"),
    /**
     * 锁定
     */
    FEMALE(1L, "女");
    @JsonValue
    private final Long value;
    private final String label;

    @JsonCreator
    public static GenderStatus fromValue(String value) {
        return EnumUtil.fromString(GenderStatus.class, value);
    }

}
