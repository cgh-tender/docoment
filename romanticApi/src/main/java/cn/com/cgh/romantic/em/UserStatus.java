package cn.com.cgh.romantic.em;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus implements DisplayedEnum {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 锁定
     */
    LOCK(1, "锁定");

    private final Integer value;
    private final String label;
}
