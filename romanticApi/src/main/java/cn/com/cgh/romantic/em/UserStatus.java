package cn.com.cgh.romantic.em;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus implements IEnum<Integer> {
    /**
     * 正常
     */
    NORMAL(0, "正常"),
    /**
     * 锁定
     */
    LOCK(1, "锁定");

    private final int value;
    private final String desc;
}
