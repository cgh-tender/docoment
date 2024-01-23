package cn.com.cgh.romantic.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * 职位表
 */
@Getter
@Setter
public class TbCfgPosition extends BasePojo{
    private String name;
    private String description;
    private Long parentId;
}
