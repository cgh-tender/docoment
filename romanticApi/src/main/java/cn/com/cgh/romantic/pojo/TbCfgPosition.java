package cn.com.cgh.romantic.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 职位表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "职位表")
public class TbCfgPosition extends TbBaseEntity{
    @Schema(description = "职位名称")
    private String name;
    @Schema(description = "职位描述")
    private String description;
    @Schema(description = "职位父节点")
    private Long parentId;
}
