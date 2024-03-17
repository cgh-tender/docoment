package cn.com.cgh.romantic.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesNoStatus implements DisplayedEnum {
    /**
     * 是
     */
    YES(1, "是"),
    /**
     * 否
     */
    NO(0, "否");

    private final Integer value;
    private final String label;
}
