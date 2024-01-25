package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 职位表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色-用户组关系表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbRoleGroup extends TbBaseEntity {
    @Schema(description = "用户角色id")
    private Long roleId;
    @Schema(description = "用户组id")
    private Long groupId;
}
