package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 职位表
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "职位表")
@TableName("tb_cfg_position")
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
