package cn.com.cgh.romantic.em;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author cgh
 */
@AllArgsConstructor
@Getter
public enum LoginStatus implements DisplayedEnum {
    /**
     * 正常
     */
    SUCCESS(0L, "登录成功"),
    /**
     * 登录失败
     */
    ERROR(1L, "登录失败"),
    /**
     * 登录失败
     */
    IN(2L, "正在登录"),
    /**
     * 登录失败
     */
    LOGOUT(3L, "退出登录");
    @Getter
    private final Long value;
    private final String label;

}
