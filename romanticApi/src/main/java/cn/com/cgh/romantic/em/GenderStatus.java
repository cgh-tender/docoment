package cn.com.cgh.romantic.em;

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
    MALE(0, "男"),
    /**
     * 锁定
     */
    FEMALE(1, "女");
    private final Integer value;
    private final String label;

}
