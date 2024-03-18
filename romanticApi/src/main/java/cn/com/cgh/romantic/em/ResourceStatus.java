package cn.com.cgh.romantic.em;

import cn.hutool.core.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cgh
 */

@AllArgsConstructor
@Getter
public enum ResourceStatus implements DisplayedEnum {
    /**
     * 菜单
     */
    MENU(0L, "菜单"),
    /**
     * 按钮
     */
    BUTTON(1L, "按钮");

    @JsonValue
    private final Long value;
    private final String label;

    @JsonCreator
    public static ResourceStatus fromValue(String value) {
        return EnumUtil.fromString(ResourceStatus.class, value);
    }
}
