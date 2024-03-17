package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.resource.TbCfgPosition;
import cn.com.cgh.romantic.pojo.resource.TbUserPosition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户-职位关系表 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
public interface ITbUserPositionService extends IService<TbUserPosition> {

    int deleteByUserId(Long userId);

    boolean insertByUserId(List<TbCfgPosition> positions, Long userId);
}
