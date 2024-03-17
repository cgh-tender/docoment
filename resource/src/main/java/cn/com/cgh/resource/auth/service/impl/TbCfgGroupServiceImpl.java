package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.resource.auth.mapper.TbCfgGroupMapper;
import cn.com.cgh.resource.auth.service.ITbCfgGroupService;
import cn.com.cgh.resource.auth.service.ITbUserGroupService;
import cn.com.cgh.romantic.pojo.SelectOption;
import cn.com.cgh.romantic.pojo.resource.TbCfgGroup;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * <p>
 * 用户组表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RestController
@Service
@Slf4j
public class TbCfgGroupServiceImpl extends ServiceImpl<TbCfgGroupMapper, TbCfgGroup> implements ITbCfgGroupService {
    @Autowired
    private ITbUserGroupService tbUserGroupService;
    @Override
    public List<SelectOption> getAllGroup() {
        return baseMapper.getAllGroup();
    }

    @Override
    public Future<String> addOrUpdate(List<TbCfgGroup> groups, Long userId) {
        return CompletableFuture.supplyAsync(() -> {
            tbUserGroupService.deleteByUserId(userId);
            tbUserGroupService.insertByUserId(groups,userId);
            return "success";
        });
    }

    @Override
    public Future<String> removeByUserId(Long userId) {
        return CompletableFuture.supplyAsync(() -> {
            tbUserGroupService.deleteByUserId(userId);
            return "success";
        });
    }
}
