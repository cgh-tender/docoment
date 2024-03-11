package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public TbCfgUser queryOneByUsername(@PathVariable String username);

    @GetMapping
    public Page<TbCfgUser> get(TbCfgUser user, int currentPage, int size);

    @PostMapping
    public String add(TbCfgUser user);
}
