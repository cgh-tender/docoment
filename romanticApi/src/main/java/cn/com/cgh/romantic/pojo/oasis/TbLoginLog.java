package cn.com.cgh.romantic.pojo.oasis;

import cn.com.cgh.romantic.em.LoginStatus;
import cn.com.cgh.romantic.pojo.TbBaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.EnumTypeHandler;

/**
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "记录登录操作")
@Accessors(chain = true)
@TableName("tb_login_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TbLoginLog extends TbBaseEntity {
    @Schema(description = "记录登录的用户名")
    private String username;
    @Schema(description = "录请求的客户端 IP 地址")
    private String clientIp;
    @Schema(description = "登出时间")
    private String logoutTime;
    /**
     * 成功登录、密码错误、账号被锁定
     */
    @Schema(description = "登录状态")
    @TableField(typeHandler = EnumTypeHandler.class)
    private LoginStatus loginStatus;
    @Schema(description = "请求的用户代理信息")
    private String userAgent;
}
