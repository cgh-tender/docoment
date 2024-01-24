package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.resource.auth.service.ITbCfgUserService;
import cn.com.cgh.romantic.em.GenderStatus;
import cn.com.cgh.romantic.em.UserStatus;
import cn.com.cgh.romantic.pojo.TbCfgUser;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@Controller
@Slf4j
@RequestMapping("/user")
public class TbCfgUserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ITbCfgUserService tbCfgUserService;

    @PostMapping
    public String add(TbCfgUser user){
        user.setUsername("admin");
        user.setPassword("123456");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.NORMAL);
        user.setPhone("18334774205");
        user.setEmail("late_tender@163.com");
        user.setGender(GenderStatus.MALE);
        tbCfgUserService.save(user);
        return "保存成功";
    }

    @GetMapping("/loadByUsername/{username}")
    public TbCfgUser loadByUsername(@PathVariable String username) {
        TbCfgUser tbCfgUser = new TbCfgUser();
        tbCfgUser.setUsername(username);
        tbCfgUser.setPassword(passwordEncoder.encode("12345678"));
        tbCfgUser.setStatus(UserStatus.NORMAL);
        log.info(JSONUtil.toJsonStr(tbCfgUser));
        return tbCfgUser;
    }
}
