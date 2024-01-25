package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 用户组表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户组")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbCfgGroup extends TbBaseEntity {
    @Schema(description = "名称")
    private String name;
}