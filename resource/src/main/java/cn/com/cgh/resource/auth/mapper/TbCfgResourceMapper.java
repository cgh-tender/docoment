package cn.com.cgh.resource.auth.mapper;

import cn.com.cgh.romantic.pojo.resource.TbCfgResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品销售统计 Mapper 接口
 * </p>
 *
 * @author cgh
 * @since 2024-01-05
 */
@Mapper
public interface TbCfgResourceMapper extends BaseMapper<TbCfgResource> {
    List<TbCfgResource> queryTbCfgResourceList(@Param("parentId") Long parentId);

    Page<TbCfgResource> queryResourceList(Page<Object> objectPage);
}
