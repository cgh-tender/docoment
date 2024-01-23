package cn.com.cgh.romantic.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "角色互斥表")
public class TbRoleExclusion extends TbBaseEntity{
    @Schema(description = "角色1")
    private Long roleIdOne;
    @Schema(description = "角色2")
    private Long roleIdTwo;
    @Schema(description = "互斥说明")
    private String description;
}
