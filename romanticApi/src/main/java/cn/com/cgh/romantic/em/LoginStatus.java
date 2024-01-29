package cn.com.cgh.romantic.em;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

public enum LoginStatus implements IEnum<Integer> {
    /**
     * 正常
     */
    LOGIN(0, "登录成功"),
    /**
     * 登录失败
     */
    ERROR(1, "登录失败");
    @Getter
    private final Integer value;
    private final String desc;

    LoginStatus(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
