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
    MALE(0L, "男"),
    /**
     * 锁定
     */
    FEMALE(1L, "女");
    private final Long value;
    private final String label;

}
