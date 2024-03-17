package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.resource.TbCfgOrganization;
import cn.com.cgh.romantic.pojo.resource.TbUserOrganization;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户-组织关系表 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
public interface ITbUserOrganizationService extends IService<TbUserOrganization> {

    int deleteByUserId(Long userId);

    boolean insertByUserId(List<TbCfgOrganization> organizations, Long userId);
}
