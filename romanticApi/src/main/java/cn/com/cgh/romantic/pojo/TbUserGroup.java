package cn.com.cgh.romantic.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户-用户组关系表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户-用户组关系表")
public class TbUserGroup extends TbBaseEntity {
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "用户组id")
    private Long groupId;
}