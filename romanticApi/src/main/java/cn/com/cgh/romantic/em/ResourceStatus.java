package cn.com.cgh.romantic.em;

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

    private final Long value;
    private final String label;
}
