package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.romantic.pojo.resource.TbCfgResource;
import cn.com.cgh.resource.auth.mapper.TbCfgResourceMapper;
import cn.com.cgh.resource.auth.service.ITbCfgResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品销售统计 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-05
 */
@Service
public class TbCfgResourceServiceImpl extends ServiceImpl<TbCfgResourceMapper, TbCfgResource> implements ITbCfgResourceService {
    @Autowired
    private TbCfgResourceMapper tbCfgResourceMapper;
    @Override
    public List<TbCfgResource> queryTbCfgResourceList() {
        return tbCfgResourceMapper.queryTbCfgResourceList(0L);
    }
}
