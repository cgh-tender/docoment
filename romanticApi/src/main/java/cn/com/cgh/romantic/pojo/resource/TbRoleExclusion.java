package cn.com.cgh.romantic.pojo.resource;

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
@Schema(description = "角色互斥表")
@TableName("tb_role_exclusion")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TbRoleExclusion extends TbBaseEntity {
    @Schema(description = "角色1")
    private Long roleIdOne;
    @Schema(description = "角色2")
    private Long roleIdTwo;
    @Schema(description = "互斥说明")
    private String description;
}
