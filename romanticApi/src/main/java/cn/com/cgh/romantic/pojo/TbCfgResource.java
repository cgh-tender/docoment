package cn.com.cgh.romantic.pojo;

import cn.com.cgh.romantic.em.DeleteStatus;
import cn.com.cgh.romantic.em.ResourceStatus;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单表
 * @author baomidou
 * @since 2024-01-05
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(description = "菜单表")
public class TbCfgResource extends TbBaseEntity {
    @Schema(description = "菜单code")
    private Long code;
    @Schema(description = "父菜单code")
    private Long parentCode;
    @Schema(description = "菜单名称")
    private String name;
    @Schema(description = "描述")
    private String description;
    @Schema(description = "url")
    private String url;
    @Schema(description = "资源类别")
    private ResourceStatus status;
    @Schema(description = "是否删除 1: 是，2：否")
    private DeleteStatus deleted;
}
