package cn.com.cgh.romantic.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cgh
 */

@Getter
@AllArgsConstructor
public enum YesNoStatus implements DisplayedEnum {
    /**
     * 是
     */
    YES(1L, "是"),
    /**
     * 否
     */
    NO(0L, "否");

    private final Long value;
    private final String label;
}
