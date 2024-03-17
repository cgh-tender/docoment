package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.resource.TbCfgRole;
import cn.com.cgh.romantic.pojo.resource.TbUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
public interface ITbUserRoleService extends IService<TbUserRole> {

    /**
     * 根据输入的用户ID删除数据，返回删除的行数。
     * @param userId 用户ID，用于指定要删除的数据。
     * @return 返回删除的行数，便于确认操作的影响范围。
     */
    int deleteByUserId(Long userId);

    /**
     * 根据用户ID插入角色信息。
     *
     * @param roles 角色信息列表，待插入数据库的角色信息。
     * @param userId 用户ID，用于指定插入角色信息的所属用户。
     * @return boolean 返回插入操作是否成功。成功返回true，失败返回false。
     */
    boolean insertByUserId(List<TbCfgRole> roles, Long userId);
}
