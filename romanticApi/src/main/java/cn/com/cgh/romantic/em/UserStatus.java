package cn.com.cgh.romantic.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus implements DisplayedEnum {
    /**
     * 正常
     */
    NORMAL(0L, "使用"),
    /**
     * 锁定
     */
    LOCK(1L, "锁定"),
    /**
     * 删除
     */
    DELETE(10L, "删除");
    private final Long value;
    private final String label;
}
