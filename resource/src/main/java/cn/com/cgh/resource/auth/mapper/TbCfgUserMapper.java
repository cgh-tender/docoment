package cn.com.cgh.resource.auth.mapper;

import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import reactor.core.publisher.Mono;

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

    Page<TbCfgUser> queryUsers(Page<TbCfgUser> page, @Param("user") TbCfgUser user,@Param("admin")  Boolean admin);
}
