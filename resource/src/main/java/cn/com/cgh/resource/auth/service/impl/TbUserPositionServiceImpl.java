package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.resource.auth.mapper.TbUserPositionMapper;
import cn.com.cgh.resource.auth.service.ITbUserPositionService;
import cn.com.cgh.romantic.pojo.resource.TbCfgPosition;
import cn.com.cgh.romantic.pojo.resource.TbUserPosition;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户-职位关系表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Service
public class TbUserPositionServiceImpl extends ServiceImpl<TbUserPositionMapper, TbUserPosition> implements ITbUserPositionService {
    @Override
    @Transactional
    public int deleteByUserId(Long userId) {
        LambdaUpdateWrapper<TbUserPosition> delete = new LambdaUpdateWrapper<>();
        delete.eq(TbUserPosition::getUserId, userId);
        return baseMapper.delete(delete);
    }

    @Override
    @Transactional
    public boolean insertByUserId(List<TbCfgPosition> groups, Long userId) {
        List<TbUserPosition> collect = groups.stream().map(group -> new TbUserPosition().setPositionId(group.getId()).setUserId(userId))
                .collect(Collectors.toList());
        return saveBatch(collect);
    }
}
