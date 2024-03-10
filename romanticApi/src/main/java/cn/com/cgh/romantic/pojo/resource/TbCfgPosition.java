package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.ToStringSummary;

/**
 * 职位表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "职位表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbCfgPosition extends TbBaseEntity {
    @Schema(description = "职位名称")
    private String name;
    @Schema(description = "职位描述")
    private String description;
    @Schema(description = "职位父节点")
    private Long parentId;
}
