package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.romantic.pojo.resource.TbCfgRole;
import cn.com.cgh.romantic.pojo.resource.TbUserGroup;
import cn.com.cgh.romantic.pojo.resource.TbUserRole;
import cn.com.cgh.resource.auth.mapper.TbUserRoleMapper;
import cn.com.cgh.resource.auth.service.ITbUserRoleService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Service
public class TbUserRoleServiceImpl extends ServiceImpl<TbUserRoleMapper, TbUserRole> implements ITbUserRoleService {
    @Override
    public int deleteByUserId(Long userId) {
        LambdaUpdateWrapper<TbUserRole> delete = new LambdaUpdateWrapper<>();
        delete.eq(TbUserRole::getUserId, userId);
        return baseMapper.delete(delete);
    }

    @Override
    @Transactional
    public boolean insertByUserId(List<TbCfgRole> roles, Long userId) {
        List<TbUserRole> collect = roles.stream().map(group -> new TbUserRole().setRoleId(group.getId()).setUserId(userId))
                .collect(Collectors.toList());
        return saveBatch(collect);
    }
}
