package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.resource.auth.mapper.TbCfgUserMapper;
import cn.com.cgh.resource.auth.service.ITbCfgUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RestController
@Service
public class TbCfgUserServiceImpl extends ServiceImpl<TbCfgUserMapper, TbCfgUser> implements ITbCfgUserService {
    @Override
    public String checkPassword(String password) {
        return null;
    }

    @Override
    public TbCfgUser queryOneByUsername(String username) {
        return baseMapper.queryOneByUsername(username);
    }


    @Override
    public Page<TbCfgUser> get(TbCfgUser user, int currentPage, int pageSize) {
        return baseMapper.selectPage(new Page(currentPage,pageSize),new QueryWrapper<>(user));
    }

    @Override
    public String add(TbCfgUser user){
        boolean save = save(user);
        return save?"添加成功":"添加失败";
    }
}
