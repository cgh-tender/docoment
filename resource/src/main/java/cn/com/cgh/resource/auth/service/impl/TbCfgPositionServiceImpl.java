package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.resource.auth.mapper.TbCfgPositionMapper;
import cn.com.cgh.resource.auth.service.ITbCfgPositionService;
import cn.com.cgh.resource.auth.service.ITbUserPositionService;
import cn.com.cgh.romantic.pojo.SelectOption;
import cn.com.cgh.romantic.pojo.resource.TbCfgPosition;
import cn.com.cgh.romantic.pojo.resource.TbCfgRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * <p>
 * 职位表 服务实现类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RestController
@Service
public class TbCfgPositionServiceImpl extends ServiceImpl<TbCfgPositionMapper, TbCfgPosition> implements ITbCfgPositionService {
    @Autowired
    private ITbUserPositionService iTbUserPositionService;
    @Override
    public List<SelectOption> getTree(Long parentId) {
        parentId = Optional.ofNullable(parentId).orElse(0L);
        return baseMapper.selectPosition(parentId);
    }

    @Override
    public Future<String> addOrUpdate(List<TbCfgPosition> positions, Long userId) {
        return CompletableFuture.supplyAsync(() -> {
            iTbUserPositionService.deleteByUserId(userId);
            iTbUserPositionService.insertByUserId(positions, userId);
            return "success";
        });
    }

    @Override
    public Future<String> removeByUserId(Long userId) {
        return CompletableFuture.supplyAsync(() -> {
            iTbUserPositionService.deleteByUserId(userId);
            return "success";
        });
    }
}
