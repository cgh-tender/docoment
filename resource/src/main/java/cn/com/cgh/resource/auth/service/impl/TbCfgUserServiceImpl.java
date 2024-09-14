package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.resource.auth.mapper.TbCfgUserMapper;
import cn.com.cgh.resource.auth.service.*;
import cn.com.cgh.romantic.config.WebfluxAOPConfig;
import cn.com.cgh.romantic.em.UserStatus;
import cn.com.cgh.romantic.em.YesNoStatus;
import cn.com.cgh.romantic.exception.ServiceException;
import cn.com.cgh.romantic.pojo.resource.*;
import cn.com.cgh.romantic.util.PermissionUserUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RestController
@Service
@Slf4j
public class TbCfgUserServiceImpl extends ServiceImpl<TbCfgUserMapper, TbCfgUser> implements ITbCfgUserService {
    @Resource
    private ITbCfgGroupService groupService;
    @Resource
    private ITbCfgOrganizationService organizationService;
    @Resource
    private ITbCfgPositionService positionService;
    @Resource
    private ITbCfgRoleService roleService;
    @Resource
    private PermissionUserUtil permissionService;

    @SneakyThrows
    @Override
    public Boolean checkPassword(String password) {
        log.info("checkPassword ... ");
        Thread.sleep(1000);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteUser(Long userId) {
        LambdaUpdateWrapper<TbCfgUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(TbCfgUser::getId, userId);
        wrapper.set(TbCfgUser::getDeleted, YesNoStatus.YES);
        wrapper.set(TbCfgUser::getStatus, UserStatus.DELETE);
        baseMapper.update(wrapper);
        groupService.removeByUserId(userId);
        organizationService.removeByUserId(userId);
        positionService.removeByUserId(userId);
        roleService.removeByUserId(userId);
        return true;
    }

    @Override
    public String resetquest() {
        return "true";
    }

    @Override
    public TbCfgUser queryOneByUsername(String username) {
        return baseMapper.queryOneByUsername(username);
    }


    @Override
    public Page<TbCfgUser> get(TbCfgUser user, int currentPage, int pageSize) {
        Long userId = WebfluxAOPConfig.RequestContextHolder.getUserId();
        Boolean admin = permissionService.admin(userId);
        return baseMapper.queryUsers(new Page<>(currentPage, pageSize), user, admin);
    }

    @Override
    public String addOrUpdate(TbCfgUser user) {
        log.info(user.toString());
        Long id = user.getId();

        /**
         * 用户组
         */
        List<TbCfgGroup> groups = user.getGroups();
        /**
         * 组织
         */
        List<TbCfgOrganization> organizations = user.getOrganizations();
        /**
         * 角色
         */
        List<TbCfgRole> roles = user.getRoles();
        /**
         * 职位
         */
        List<TbCfgPosition> positions = user.getPositions();
        if (id == null) {
            boolean save = save(user);
            if (save) {
                id = user.getId();
            } else {
                throw new ServiceException("新增失败");
            }
        } else {
            boolean save = updateById(user);
            if (!save) {
                throw new ServiceException("更新失败");
            }
        }
        groupService.addOrUpdate(groups, id);
        organizationService.addOrUpdate(organizations, id);
        positionService.addOrUpdate(positions, id);
        roleService.addOrUpdate(roles, id);
        return "success";
    }

    /**
     * 更新用户状态，并在状态为删除时，同步删除用户信息。
     *
     * @param id     用户ID，用于指定需要更新状态的用户。
     * @param status 用户状态，枚举类型，表示用户的不同状态，如正常、禁用、删除等。
     * @return 返回固定字符串"更新成功"，表示状态更新操作完成。
     */
    @Override
    public void upUserStatus(Long id, UserStatus status) {
        // 使用LambdaUpdateWrapper构造更新条件，设置用户状态为参数status，并指定更新的用户ID为参数id
        LambdaUpdateWrapper<TbCfgUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(TbCfgUser::getStatus, status);
        wrapper.eq(TbCfgUser::getId, id);
        // 执行更新操作
        baseMapper.update(wrapper);

        // 如果更新的状态为删除，则调用deleteUser方法，物理删除该用户的信息
        if (UserStatus.DELETE == status) {
            deleteUser(id);
        }
    }

}
