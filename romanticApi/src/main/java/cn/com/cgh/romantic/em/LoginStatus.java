package cn.com.cgh.romantic.em;

import lombok.Getter;

/**
 * @author cgh
 */

public enum LoginStatus implements DisplayedEnum {
    /**
     * 正常
     */
    SUCCESS(0, "登录成功"),
    /**
     * 登录失败
     */
    ERROR(1, "登录失败"),
    /**
     * 登录失败
     */
    IN(2, "正在登录");
    @Getter
    private final Integer value;
    private final String label;

    LoginStatus(Integer value, String desc) {
        this.value = value;
        this.label = desc;
    }

}
