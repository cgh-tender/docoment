package cn.com.cgh.romantic.em;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeleteStatus implements IEnum<Boolean> {
    DELETE(Boolean.TRUE, "删除"),
    /**
     * 活跃
     */
    ACTIVITY(Boolean.FALSE, "活跃");
    /**
     * 删除
     */
    private final Boolean value;
    private final String label;

}
