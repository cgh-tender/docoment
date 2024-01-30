package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.resource.auth.service.ITbCfgRoleService;
import cn.com.cgh.resource.auth.service.ITbCfgUserService;
import cn.com.cgh.romantic.pojo.MsgPojo;
import cn.com.cgh.romantic.pojo.oasis.TbControllerLog;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.romantic.util.IdWork;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.SendQueue;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.FormSubmitEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author cgh
 */
@RestController
@RequestMapping()
public class LoginController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ITbCfgUserService iTbCfgUserService;
    @Autowired
    private ITbCfgRoleService iTbCfgRoleService;
    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Map login(@RequestBody TbCfgUser user) {
        Map<String, String> map = new HashMap<>();
        map.put("token", user.getUsername());
        return map;
    }

    /**
     * user info
     *
     * @return
     */
    @GetMapping("/info")
    public Map info(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        Long userId = jwtTokenUtil.getUserIdFromToken(authorization);
        Set<String> data = iTbCfgRoleService.queryUserRoles(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("roles", data);
        return map;
    }

    @Autowired
    private SendQueue sendQueue;

    @Autowired
    private IdWork idWork;
    @GetMapping("/send")
    public String senQuery() {
        sendQueue.doSendControllerQueue(MsgPojo.builder().id(idWork.nextId()).msg(
                TbControllerLog.builder()
                        .httpMethod(FormSubmitEvent.MethodType.GET.name())
                        .requestUrl("/send")
                        .clientIp("127.0.0.1")
                        .userAgent("admin")
                        .build()
        ).build());
        return "成功";
    }
}
