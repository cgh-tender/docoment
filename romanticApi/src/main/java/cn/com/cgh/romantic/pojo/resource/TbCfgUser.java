package cn.com.cgh.romantic.pojo.resource;

import cn.com.cgh.romantic.config.aspect.annotation.RequestKeyParam;
import cn.com.cgh.romantic.em.GenderStatus;
import cn.com.cgh.romantic.em.UserStatus;
import cn.com.cgh.romantic.em.YesNoStatus;
import cn.com.cgh.romantic.pojo.TbBaseEntity;
import cn.com.cgh.romantic.typeHandler.DefaultEnumTypeHandler;
import com.baomidou.mybatisplus.annotation.SqlCondition;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgh
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户表")
@TableName("tb_cfg_user")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TbCfgUser extends TbBaseEntity implements UserDetails {
    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    @TableField(condition = SqlCondition.LIKE)
    @RequestKeyParam
    private String username;
    /**
     * 用户姓名
     */
    @Schema(description = "用户姓名")
    private String realname;
    /**
     * 用户密码
     */
    @Schema(description = "密码")
    private String password;
    /**
     * 用户状态
     */
    @Schema(description = "用户状态 0 : 正常，1锁定")
    @TableField(typeHandler = DefaultEnumTypeHandler.class)
    private UserStatus status;
    /**
     * 手机号
     */
    @Schema(description = "手机号")
    @TableField(condition = SqlCondition.LIKE)
    private String phone;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;
    /**
     * 性别
     */
    @Schema(description = "性别：0男 1女")
    @TableField(typeHandler = DefaultEnumTypeHandler.class)
    private GenderStatus gender;

    @Schema(description = "是否删除：0否 1是")
    @TableField(typeHandler = DefaultEnumTypeHandler.class)
    private YesNoStatus deleted;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private List<TbCfgRole> roles;
    /**
     * 组织列表
     */
    @TableField(exist = false)
    private List<TbCfgOrganization> organizations;
    /**
     * 用户组列表
     */
    @TableField(exist = false)
    private List<TbCfgGroup> groups;
    /**
     * 职位列表
     */
    @TableField(exist = false)
    private List<TbCfgPosition> positions;
    @TableField(exist = false)
    private String code;
    @TableField(exist = false)
    private String clientId;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(TbCfgRole::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return status.equals(UserStatus.NORMAL);
    }
}