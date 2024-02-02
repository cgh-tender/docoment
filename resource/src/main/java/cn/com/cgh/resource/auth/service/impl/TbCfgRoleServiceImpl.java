package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.resource.auth.mapper.TbCfgRoleMapper;
import cn.com.cgh.resource.auth.service.ITbCfgRoleService;
import cn.com.cgh.romantic.pojo.resource.TbCfgRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Service
public class TbCfgRoleServiceImpl extends ServiceImpl<TbCfgRoleMapper, TbCfgRole> implements ITbCfgRoleService {
    @Override
    public Set<String> queryUserRoles(Long userId) {
        return baseMapper.queryAllByUserId(userId).stream().map(TbCfgRole::getName).collect(Collectors.toSet());
    }
}