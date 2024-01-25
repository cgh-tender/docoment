package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.resource.auth.service.ITbCfgUserService;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class TbCfgUserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ITbCfgUserService tbCfgUserService;

    @PostMapping
    public String add(TbCfgUser user){
        boolean save = tbCfgUserService.save(user);
        return save?"添加成功":"添加失败";
    }

    @GetMapping("/loadByUsername/{username}")
    public TbCfgUser loadByUsername(@PathVariable String username) {
        return tbCfgUserService.queryOneByUsername(username);
    }
}
