package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.em.UserStatus;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RequestMapping("/user")
public interface ITbCfgUserService extends IService<TbCfgUser> {
    @GetMapping("/loadByUsername/{username}")
    public Mono<TbCfgUser> queryOneByUsername(@PathVariable String username);
    @GetMapping("/resetquest")
    public String resetquest();

    @GetMapping("/checkPassword/{password}")
    public Boolean checkPassword(@PathVariable String password);

    @GetMapping("/deleteUser/{userId}")
    public Boolean deleteUser(@PathVariable Long userId);

    @GetMapping
    public Page<TbCfgUser> get(TbCfgUser user, int currentPage, int pageSize);

    @PostMapping
    public String addOrUpdate(@RequestBody TbCfgUser user);

    @PostMapping("/upUserStatus/{id}/{status}")
    public void upUserStatus(@PathVariable Long id,@PathVariable UserStatus status);
}
