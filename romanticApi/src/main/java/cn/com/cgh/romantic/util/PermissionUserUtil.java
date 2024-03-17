package cn.com.cgh.romantic.util;


import cn.com.cgh.romantic.config.PermissionConfig;
import com.alibaba.cloud.commons.lang.StringUtils;
import jakarta.annotation.Resource;

import java.util.*;

public class PermissionUserUtil {
    private static final Set<Object> ADMIN = new HashSet();
    static {
        ADMIN.add("admin");
        ADMIN.add(1L);
    }
    @Resource
    private PermissionConfig permissionConfig;

    public Collection<Object> getAdminUser(){
        Set<Object> adminUser = permissionConfig.getAdminUser();
        return Optional.ofNullable(adminUser).flatMap(us -> {
            us.addAll(ADMIN);
            return Optional.of(us);
        }).orElse(ADMIN);
    }

    /**
     * 检查指定用户是否为管理员。
     *
     * @param userFlag 用户的标识。
     * @return 返回一个布尔值，如果用户是管理员，则返回true；否则返回false。
     */
    public Boolean admin(Object userFlag) {
        // 检查管理员用户列表中是否包含指定的用户ID
        return getAdminUser().stream().anyMatch(item -> StringUtils.equals(String.valueOf(item), String.valueOf(userFlag)));
    }
}
