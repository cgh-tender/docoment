package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 职位表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色-职位关系表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbRolePosition extends TbBaseEntity {
    @Schema(description = "用户角色id")
    private Long roleId;
    @Schema(description = "组织id")
    private Long positionId;
}
