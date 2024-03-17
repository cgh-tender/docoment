package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.romantic.pojo.resource.TbCfgGroup;
import cn.com.cgh.romantic.pojo.resource.TbCfgOrganization;
import cn.com.cgh.romantic.pojo.resource.TbUserGroup;
import cn.com.cgh.romantic.pojo.resource.TbUserOrganization;
import cn.com.cgh.resource.auth.mapper.TbUserOrganizationMapper;
import cn.com.cgh.resource.auth.service.ITbUserOrganizationService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户-组织关系表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Service
public class TbUserOrganizationServiceImpl extends ServiceImpl<TbUserOrganizationMapper, TbUserOrganization> implements ITbUserOrganizationService {
    @Override
    public int deleteByUserId(Long userId) {
        LambdaUpdateWrapper<TbUserOrganization> delete = new LambdaUpdateWrapper<>();
        delete.eq(TbUserOrganization::getUserId, userId);
        return baseMapper.delete(delete);
    }

    @Override
    @Transactional
    public boolean insertByUserId(List<TbCfgOrganization> groups, Long userId) {
        List<TbUserOrganization> collect = groups.stream().map(group -> new TbUserOrganization().setOrganizationId(group.getId()).setUserId(userId))
                .collect(Collectors.toList());
        return saveBatch(collect);
    }
}
