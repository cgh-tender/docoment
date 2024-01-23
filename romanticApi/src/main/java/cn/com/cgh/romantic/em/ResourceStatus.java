package cn.com.cgh.romantic.em;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResourceStatus implements IEnum<Integer> {
    MENU(0, "菜单"),
    BUTTON(1, "按钮");

    private final int value;
    private final String desc;
}
