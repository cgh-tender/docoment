package cn.com.cgh.resource.auth.mapper;

import cn.com.cgh.romantic.pojo.resource.TbCfgRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组织表 Mapper 接口
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Mapper
public interface TbCfgRoleMapper extends BaseMapper<TbCfgRole> {

    List<TbCfgRole> queryAllByUserId(@Param("userId")Long userId);
}
