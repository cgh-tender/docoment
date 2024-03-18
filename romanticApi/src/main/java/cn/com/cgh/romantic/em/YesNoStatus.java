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
public enum YesNoStatus implements DisplayedEnum {
    /**
     * 是
     */
    YES(1L, "是"),
    /**
     * 否
     */
    NO(0L, "否");
    @JsonValue
    private final Long value;
    private final String label;

    @JsonCreator
    public static YesNoStatus fromValue(String value) {
        return EnumUtil.fromString(YesNoStatus.class, value);
    }
}
