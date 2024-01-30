package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.resource.TbCfgRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 组织表 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
public interface ITbCfgRoleService extends IService<TbCfgRole> {

    Set<String> queryUserRoles(Long userId);
}
