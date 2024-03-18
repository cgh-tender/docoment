package cn.com.cgh.romantic.em;

import cn.hutool.core.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus implements DisplayedEnum {
    /**
     * 正常
     */
    NORMAL(0L, "使用"),
    /**
     * 锁定
     */
    LOCK(1L, "锁定"),
    /**
     * 删除
     */
    DELETE(10L, "删除");
    @JsonValue
    private final Long value;
    private final String label;

    @JsonCreator
    public static UserStatus fromValue(String value) {
        return EnumUtil.fromString(UserStatus.class, value);
    }
}
