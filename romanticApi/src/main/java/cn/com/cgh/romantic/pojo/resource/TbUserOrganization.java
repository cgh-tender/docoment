package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 用户-组织关系表
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户-组织关系表")
@TableName("tb_user_organization")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TbUserOrganization extends TbBaseEntity {
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "组织id")
    private Long organizationId;
}
