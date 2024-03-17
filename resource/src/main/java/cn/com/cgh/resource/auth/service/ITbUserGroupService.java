package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.resource.TbCfgGroup;
import cn.com.cgh.romantic.pojo.resource.TbUserGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户组表 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
public interface ITbUserGroupService extends IService<TbUserGroup> {
    /**
     * 根据用户ID删除相关记录。
     *
     * @param userId 用户ID，用于指定要删除的数据。
     * @return 返回删除的记录数。
     */
    int deleteByUserId(Long userId);

    /**
     * 根据用户ID插入配置组信息。
     *
     * @param groups 配置组信息列表，需要插入到数据库中的配置组。
     * @param userId 用户ID，标识这些配置组属于哪个用户。
     * @return boolean 返回插入操作是否成功。如果成功插入至少一个配置组，则返回true；如果所有配置组插入失败或未插入任何配置组，则返回false。
     */
    boolean insertByUserId(List<TbCfgGroup> groups, Long userId);
}
