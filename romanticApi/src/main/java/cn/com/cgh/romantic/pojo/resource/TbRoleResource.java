package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.em.ResourceStatus;
import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色资源关系表")
@TableName("tb_role_resource")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TbRoleResource extends TbBaseEntity {
    @Schema(description = "角色id")
    private Long roleId;
    @Schema(description = "资源id")
    private Long resourceId;
    @Schema(description = "资源类别")
    private ResourceStatus status;
}
