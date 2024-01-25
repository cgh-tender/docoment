package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 用户-职位关系表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户-职位关系表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbUserPosition extends TbBaseEntity {
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "组织id")
    private Long positionId;
}
