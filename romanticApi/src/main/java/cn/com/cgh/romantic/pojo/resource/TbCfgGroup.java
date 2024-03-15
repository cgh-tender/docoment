package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 用户组表
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户组")
@TableName("tb_cfg_group")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbCfgGroup extends TbBaseEntity {
    @Schema(description = "名称")
    private String name;
}