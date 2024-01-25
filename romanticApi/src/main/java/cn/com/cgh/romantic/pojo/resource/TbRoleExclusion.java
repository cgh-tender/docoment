package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色互斥表")
@Builder
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
