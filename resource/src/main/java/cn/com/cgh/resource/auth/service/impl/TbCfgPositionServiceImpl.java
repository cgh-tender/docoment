package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.resource.auth.mapper.TbCfgPositionMapper;
import cn.com.cgh.resource.auth.service.ITbCfgPositionService;
import cn.com.cgh.romantic.pojo.resource.TbCfgPosition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RestController
@Service
public class TbCfgPositionServiceImpl extends ServiceImpl<TbCfgPositionMapper, TbCfgPosition> implements ITbCfgPositionService {
    @Override
    public String getTest() {
        return "test";
    }
}
