package cn.com.cgh.romantic.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 组织表
 */
@Data
@Schema(description = "组织表")
public class TbCfgOrganization extends TbBaseEntity{
    @Schema(description = "组织名称")
    private String name;
    @Schema(description = "组织描述")
    private String description;
    @Schema(description = "组织父节点")
    private Long parentId;
}
