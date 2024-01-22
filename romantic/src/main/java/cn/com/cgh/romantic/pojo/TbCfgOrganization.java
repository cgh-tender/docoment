package cn.com.cgh.romantic.pojo;


import lombok.Getter;
import lombok.Setter;

/**
 * 组织表
 */
@Getter
@Setter
public class TbCfgOrganization extends BasePojo{
    private String name;
    private String description;
    private Long parentId;
}
