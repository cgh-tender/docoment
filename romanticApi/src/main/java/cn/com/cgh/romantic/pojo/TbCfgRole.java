package cn.com.cgh.romantic.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色表")
public class TbCfgRole extends TbBaseEntity{
   @Schema(description = "角色名称")
   private String name;
   @Schema(description = "角色描述")
   private String description;
   @Schema(description = "角色描述")
   private Long parentId;
}
