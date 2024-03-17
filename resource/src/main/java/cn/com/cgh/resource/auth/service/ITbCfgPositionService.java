package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.SelectOption;
import cn.com.cgh.romantic.pojo.resource.TbCfgPosition;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.Future;

/**
 * <p>
 * 职位表 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RequestMapping("/position")
public interface ITbCfgPositionService extends IService<TbCfgPosition> {
    @GetMapping("/getTree")
    public List<SelectOption> getTree(Long parentId);


    @PostMapping
    @Async
    public Future<String> addOrUpdate(List<TbCfgPosition> positions, Long userId);

    @PostMapping("/remove/{userId}")
    @Async
    public Future<String> removeByUserId(@PathVariable Long userId);
}
