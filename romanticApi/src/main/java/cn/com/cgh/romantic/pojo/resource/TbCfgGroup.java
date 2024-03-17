package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 用户组表
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Schema(description = "用户组")
@TableName("tb_cfg_group")
@NoArgsConstructor
@AllArgsConstructor
public class TbCfgGroup extends TbBaseEntity {
    @Schema(description = "名称")
    private String name;
}