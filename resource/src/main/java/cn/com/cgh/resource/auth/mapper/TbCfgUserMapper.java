package cn.com.cgh.resource.auth.mapper;

import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Mapper
public interface TbCfgUserMapper extends BaseMapper<TbCfgUser> {

    TbCfgUser queryOneByUsername(@Param("username") String username);
}