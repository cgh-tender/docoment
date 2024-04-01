package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.resource.TbCfgResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品销售统计 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-05
 */
public interface ITbCfgResourceService extends IService<TbCfgResource> {

    List<TbCfgResource> queryTbCfgResourceList();

    Page<TbCfgResource> queryResourceList(int currentPage, int pageSize);
}
