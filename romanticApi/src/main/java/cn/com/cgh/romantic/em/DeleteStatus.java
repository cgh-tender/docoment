package cn.com.cgh.romantic.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeleteStatus implements DisplayedEnum {
    DELETE(1L, "删除"),
    /**
     * 活跃
     */
    ACTIVITY(0L, "活跃");
    /**
     * 删除
     */
    private final Long value;
    private final String label;
}
