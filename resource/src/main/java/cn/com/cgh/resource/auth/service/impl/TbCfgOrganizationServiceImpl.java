package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.resource.auth.mapper.TbCfgOrganizationMapper;
import cn.com.cgh.resource.auth.service.ITbCfgOrganizationService;
import cn.com.cgh.resource.auth.service.ITbUserOrganizationService;
import cn.com.cgh.romantic.pojo.SelectOption;
import cn.com.cgh.romantic.pojo.resource.TbCfgOrganization;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

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
public class TbCfgOrganizationServiceImpl extends ServiceImpl<TbCfgOrganizationMapper, TbCfgOrganization> implements ITbCfgOrganizationService {
    @Autowired
    private ITbUserOrganizationService identityService;
    @Override
    public List<SelectOption> getTree(Long parentId) {
        parentId = Optional.ofNullable(parentId).orElse(0L);
        return baseMapper.selectOption(parentId);
    }

    @Override
    public Future<String> addOrUpdate(List<TbCfgOrganization> organizations, Long userId) {
        return CompletableFuture.supplyAsync(() -> {
            identityService.deleteByUserId(userId);
            identityService.insertByUserId(organizations, userId);
            return "success";
        });
    }

    @Override
    public Future<String> removeByUserId(Long userId) {
        return CompletableFuture.supplyAsync(() -> {
            identityService.deleteByUserId(userId);
            return "success";
        });
    }
}
