package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.romantic.pojo.resource.TbCfgGroup;
import cn.com.cgh.romantic.pojo.resource.TbUserGroup;
import cn.com.cgh.resource.auth.mapper.TbUserGroupMapper;
import cn.com.cgh.resource.auth.service.ITbUserGroupService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户组表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Service
public class TbUserGroupServiceImpl extends ServiceImpl<TbUserGroupMapper, TbUserGroup> implements ITbUserGroupService {
    @Override
    @Transactional
    public int deleteByUserId(Long userId) {
        LambdaUpdateWrapper<TbUserGroup> delete = new LambdaUpdateWrapper<>();
        delete.eq(TbUserGroup::getUserId, userId);
        return baseMapper.delete(delete);
    }

    @Override
    @Transactional
    public boolean insertByUserId(List<TbCfgGroup> groups, Long userId) {
        List<TbUserGroup> collect = groups.stream().map(group -> new TbUserGroup().setGroupId(group.getId()).setUserId(userId))
                .collect(Collectors.toList());
        return saveBatch(collect);
    }
}
