package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.SelectOption;
import cn.com.cgh.romantic.pojo.resource.TbCfgRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * <p>
 * 组织表 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */

@RequestMapping("/role")
public interface ITbCfgRoleService extends IService<TbCfgRole> {

    Set<Long> queryUserRoles(Long userId);

    @GetMapping
    List<SelectOption> getUserRoles();

    @GetMapping("/getTree")
    public List<SelectOption> getTree(Long parentId);

    @PostMapping
    @Async
    public Future<String> addOrUpdate(List<TbCfgRole> roles, Long userId);

    @PostMapping("/remove/{userId}")
    @Async
    public Future<String> removeByUserId(@PathVariable Long userId);
}
