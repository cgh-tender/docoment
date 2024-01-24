package cn.com.cgh.romantic.pojo;

import cn.com.cgh.romantic.em.ResourceStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色资源关系表")
public class TbRoleResource extends TbBaseEntity{
    @Schema(description = "角色id")
    private Long roleId;
    @Schema(description = "资源id")
    private Long resourceId;
    @Schema(description = "资源类别")
    private ResourceStatus status;
}
