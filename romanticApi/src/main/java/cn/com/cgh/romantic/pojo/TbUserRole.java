package cn.com.cgh.romantic.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户-角色表关系表
 */
@Data
@Schema(description = "用户-角色表")
public class TbUserRole extends TbBaseEntity{
   @Schema(description = "用户id")
   private Long userId;
   @Schema(description = "用户角色id")
   private Long roleId;
}
