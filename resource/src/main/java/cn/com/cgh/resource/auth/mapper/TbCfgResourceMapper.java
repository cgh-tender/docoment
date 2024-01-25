package cn.com.cgh.resource.auth.mapper;

import cn.com.cgh.romantic.pojo.resource.TbCfgResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
