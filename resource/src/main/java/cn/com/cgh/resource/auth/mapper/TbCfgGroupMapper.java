package cn.com.cgh.resource.auth.mapper;

import cn.com.cgh.romantic.pojo.SelectOption;
import cn.com.cgh.romantic.pojo.resource.TbCfgGroup;
import cn.com.cgh.romantic.pojo.resource.TbCfgOrganization;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户组表 Mapper 接口
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Mapper
public interface TbCfgGroupMapper extends BaseMapper<TbCfgGroup> {

    List<TbCfgGroup> queryAllByUserId(@Param("userId")Long userId);
    @Select("SELECT id as value,name as label FROM tb_cfg_group")
    List<SelectOption> getAllGroup();
}
