package cn.com.cgh.romantic.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户-职位关系表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户-职位关系表")
public class TbUserPosition extends TbBaseEntity{
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "组织id")
    private Long positionId;
}
