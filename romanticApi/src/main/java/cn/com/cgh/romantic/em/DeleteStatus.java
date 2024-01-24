package cn.com.cgh.romantic.em;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeleteStatus implements IEnum<Integer> {
    DELETE(1, "删除"),
    /**
     * 活跃
     */
    ACTIVITY(0, "活跃");
    /**
     * 删除
     */
    private final Integer value;
    private final String desc;

}
