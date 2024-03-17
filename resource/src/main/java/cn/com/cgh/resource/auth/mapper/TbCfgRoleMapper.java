package cn.com.cgh.resource.auth.mapper;

import cn.com.cgh.romantic.pojo.SelectOption;
import cn.com.cgh.romantic.pojo.resource.TbCfgRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("SELECT id as value,name as label FROM tb_cfg_role")
    List<SelectOption> getUserRoles();

    @Select("SELECT id as value,name as label FROM tb_cfg_role WHERE PARENT_ID = ${parentId}")
    List<SelectOption> selectPosition(Long parentId);
}
