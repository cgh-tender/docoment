package cn.com.cgh.resource.auth.mapper;

import cn.com.cgh.romantic.pojo.SelectOption;
import cn.com.cgh.romantic.pojo.resource.TbCfgOrganization;
import cn.com.cgh.romantic.pojo.resource.TbCfgRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TbCfgOrganizationMapper extends BaseMapper<TbCfgOrganization> {

    List<TbCfgOrganization> queryAllByUserId(@Param("userId")Long userId);
    @Select("SELECT id as value,name as label FROM tb_cfg_organization WHERE PARENT_ID = ${parentId}")
    List<SelectOption> selectOption(@Param("parentId")Long parentId);
}
