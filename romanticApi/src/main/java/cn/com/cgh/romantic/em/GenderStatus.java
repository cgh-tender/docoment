package cn.com.cgh.romantic.em;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderStatus implements IEnum<Integer> {
    /**
     * 正常
     */
    MALE(0, "男"),
    /**
     * 锁定
     */
    FEMALE(1, "女");
    private final int value;
    private final String desc;
}
