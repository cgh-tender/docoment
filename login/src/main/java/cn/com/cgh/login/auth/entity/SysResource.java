package cn.com.cgh.login.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "SysResource对象", description = "商品销售统计")
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("菜单code")
    @TableField("code")
    private Long code;

    @ApiModelProperty("父菜单code")
    @TableField("parent_code")
    private Long parentCode;

    @ApiModelProperty("菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("desc")
    @TableField("displayName")
    private String displayName;

    @ApiModelProperty("创建人")
    @TableField("create_user")
    private Long createUser;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("最终修改人")
    @TableField("modify_user")
    private Long modifyUser;

    @ApiModelProperty("修改时间")
    @TableField("modify_time")
    private LocalDateTime modifyTime;

    @ApiModelProperty("是否删除 1: 是，2：否")
    @TableField("is_delete")
    private Byte isDelete;
}
