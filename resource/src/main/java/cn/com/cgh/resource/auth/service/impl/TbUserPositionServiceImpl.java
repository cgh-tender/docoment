package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.romantic.pojo.resource.TbUserPosition;
import cn.com.cgh.resource.auth.mapper.TbUserPositionMapper;
import cn.com.cgh.resource.auth.service.ITbUserPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
