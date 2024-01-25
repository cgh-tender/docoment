package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.resource.auth.mapper.TbCfgUserMapper;
import cn.com.cgh.resource.auth.service.ITbCfgUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Service
public class TbCfgUserServiceImpl extends ServiceImpl<TbCfgUserMapper, TbCfgUser> implements ITbCfgUserService {
    @Override
    public TbCfgUser queryOneByUsername(String username) {
        return baseMapper.queryOneByUsername(username);
    }
}
