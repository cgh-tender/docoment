package cn.com.cgh.romantic.pojo.oasis;

import cn.com.cgh.romantic.em.LoginStatus;
import cn.com.cgh.romantic.pojo.TbBaseEntity;
import cn.com.cgh.romantic.typeHandler.DefaultEnumTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Schema(description = "记录登录操作")
@Accessors(chain = true)
@TableName("tb_login_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbLoginLog extends TbBaseEntity {
    @Schema(description = "记录登录的用户名")
    private String username;
    @Schema(description = "记录登录的用户Id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    @Schema(description = "客户端IP")
    private String clientIp;
    @Schema(description = "IP地址")
    private String ipDetail;
    @Schema(description = "登出时间")
    private LocalDateTime logoutTime;
    /**
     * 成功登录、密码错误、账号被锁定
     */
    @Schema(description = "登录状态")
    @TableField(typeHandler = DefaultEnumTypeHandler.class)
    private LoginStatus loginStatus;
    @Schema(description = "请求的用户代理信息")
    private String userAgent;
    @Schema(description = "是否移动端")
    private Boolean mobile;
    @Schema(description = "操作系统")
    private String osSys;
    @Schema(description = "操作平台")
    private String browser;
    @Schema(description = "操作内核")
    private String engine;
}
