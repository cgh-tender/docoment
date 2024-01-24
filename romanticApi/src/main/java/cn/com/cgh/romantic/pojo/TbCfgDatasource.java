package cn.com.cgh.romantic.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 数据库连接表
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tb_cfg_datasource")
@Schema(name = "TbCfgDatasource对象", description = "数据库连接表")
public class TbCfgDatasource implements Serializable {

    @Schema(name = "数据库连接地址")
    @TableField("url")
    private String url;

    @Schema(name = "数据库连接用户名")
    @TableField("username")
    private String username;

    @Schema(name = "数据库连接密码")
    @TableField("password")
    private String password;
}
