package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 角色表
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Schema(description = "角色表")
@TableName("tb_cfg_role")
@NoArgsConstructor
@AllArgsConstructor
public class TbCfgRole extends TbBaseEntity {
   @Schema(description = "角色名称")
   private String name;
   @Schema(description = "角色描述")
   private String description;
   @Schema(description = "父级角色")
   private Long parentId;

   @TableField(exist = false)
   private List<TbCfgRole> children;
}
