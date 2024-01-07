package cn.com.cgh.login.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品销售统计
 * </p>
 *
 * @author baomidou
 * @since 2024-01-05
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_resource")
@Schema(description = "商品销售统计")
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "菜单code")
    @TableField("code")
    private Long code;

    @Schema(description = "父菜单code")
    @TableField("parent_code")
    private Long parentCode;

    @Schema(description = "菜单名称")
    @TableField("name")
    private String name;

    @Schema(description = "desc")
    @TableField("displayName")
    private String displayName;

    @Schema(description = "创建人")
    @TableField("create_user")
    private Long createUser;

    @Schema(description = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @Schema(description = "最终修改人")
    @TableField("modify_user")
    private Long modifyUser;

    @Schema(description = "修改时间")
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    @Schema(description = "是否删除 1: 是，2：否", example = "1")
    @TableField("is_delete")
    private Byte isDelete;
}
