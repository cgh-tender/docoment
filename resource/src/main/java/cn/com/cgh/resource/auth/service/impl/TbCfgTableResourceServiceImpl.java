package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.resource.auth.mapper.TbCfgTableResourceMapper;
import cn.com.cgh.resource.auth.service.ITbCfgTableResourceService;
import cn.com.cgh.romantic.pojo.resource.TbCfgTableResource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 表资源 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RestController
@Service
public class TbCfgTableResourceServiceImpl extends ServiceImpl<TbCfgTableResourceMapper, TbCfgTableResource> implements ITbCfgTableResourceService {
    @Override
    public boolean saves(TbCfgTableResource tableResource) {
        return save(tableResource);
    }
}
