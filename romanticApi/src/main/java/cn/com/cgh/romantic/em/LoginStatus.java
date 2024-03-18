package cn.com.cgh.romantic.em;

import cn.hutool.core.util.EnumUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
    @JsonValue
    private final Long value;
    private final String label;

    @JsonCreator
    public static LoginStatus fromValue(String value) {
        return EnumUtil.fromString(LoginStatus.class, value);
    }

}
