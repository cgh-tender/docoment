package cn.com.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserRole {
    private Integer roleId; // 编号
    private String role; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    private Boolean available = Boolean.TRUE; // 是否可用,如果不可用将不会添加给用户
    private List<Permission> permissions;
}
