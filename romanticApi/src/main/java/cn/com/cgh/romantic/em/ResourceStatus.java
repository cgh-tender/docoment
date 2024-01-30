package cn.com.cgh.romantic.em;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResourceStatus implements DisplayedEnum {
    /**
     * 菜单
     */
    MENU(0, "菜单"),
    /**
     * 按钮
     */
    BUTTON(1, "按钮");

    private final Integer value;
    private final String label;
}
