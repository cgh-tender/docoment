package cn.com.entity;

import lombok.Data;

import java.util.List;

@Data
public class Permission {
    private Integer permissionId;//主键.
    private String permissionName;//名称.
    private String resourceType;//资源类型，[menu|button]
    private String url;//资源路径.
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private Long parentId; //父编号
    private String parentIds; //父编号列表
    private Boolean available = Boolean.TRUE;
    private List<UserRole> roles;
}
