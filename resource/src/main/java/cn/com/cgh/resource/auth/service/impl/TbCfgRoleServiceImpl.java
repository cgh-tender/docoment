package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.resource.auth.mapper.TbCfgRoleMapper;
import cn.com.cgh.resource.auth.service.ITbCfgRoleService;
import cn.com.cgh.resource.auth.service.ITbUserRoleService;
import cn.com.cgh.romantic.pojo.SelectOption;
import cn.com.cgh.romantic.pojo.resource.TbCfgRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * <p>
 * 组织表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RestController
@Service
@Slf4j
public class TbCfgRoleServiceImpl extends ServiceImpl<TbCfgRoleMapper, TbCfgRole> implements ITbCfgRoleService {
    @Autowired
    private ITbUserRoleService iTbUserRoleService;
    @Override
    public Set<Long> queryUserRoles(Long userId) {
        return baseMapper.queryAllByUserId(userId).stream().map(TbCfgRole::getId).collect(Collectors.toSet());
    }

    @Override
    public List<SelectOption> getUserRoles() {
        return baseMapper.getUserRoles();
    }

    @Override
    public List<SelectOption> getTree(Long parentId) {
        parentId = Optional.ofNullable(parentId).orElse(0L);
        return baseMapper.selectPosition(parentId);
    }

    @Override
    public Future<String> addOrUpdate(List<TbCfgRole> roles, Long userId) {
        return CompletableFuture.supplyAsync(() -> {
            iTbUserRoleService.deleteByUserId(userId);
            iTbUserRoleService.insertByUserId(roles, userId);
            return "success";
        });
    }

    @Override
    public Future<String> removeByUserId(Long userId) {
        return CompletableFuture.supplyAsync(() -> {
            iTbUserRoleService.deleteByUserId(userId);
            return "success";
        });
    }
}
