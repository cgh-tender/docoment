package cn.com.cgh.romantic.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色-组织关系表
 */
@Data
@Schema(description = "角色-组织关系表")
public class TbRoleOrganization extends TbBaseEntity{
   @Schema(description = "用户角色id")
   private Long roleId;
   @Schema(description = "组织id")
   private Long organizationId;
}
