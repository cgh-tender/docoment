package cn.com.cgh.romantic.em;

import cn.hutool.core.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
    @JsonValue
    private final Long value;
    private final String label;

    @JsonCreator
    public static DeleteStatus fromValue(String value) {
        return EnumUtil.fromString(DeleteStatus.class, value);
    }
}
