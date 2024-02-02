package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 角色表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "角色表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbCfgRole extends TbBaseEntity {
   @Schema(description = "角色名称")
   private String name;
   @Schema(description = "角色描述")
   private String description;
   @Schema(description = "角色描述")
   private Long parentId;

   @TableField(exist = false)
   private List<TbCfgRole> children;
}