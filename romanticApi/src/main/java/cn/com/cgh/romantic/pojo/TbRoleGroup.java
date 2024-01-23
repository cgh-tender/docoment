package cn.com.cgh.romantic.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 职位表
 */
@Data
@Schema(description = "角色-用户组关系表")
public class TbRoleGroup extends TbBaseEntity{
    @Schema(description = "用户角色id")
    private Long roleId;
    @Schema(description = "用户组id")
    private Long groupId;
}
