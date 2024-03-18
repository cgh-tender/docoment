package cn.com.cgh.romantic.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SelectOption {
    private Long value;
    private String label;
    private boolean disabled;
}
