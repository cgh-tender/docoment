package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.core.util.IdWork;
import cn.com.cgh.romantic.pojo.MsgPojo;
import cn.com.cgh.romantic.pojo.oasis.TbControllerLog;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.romantic.util.SendQueue;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.FormSubmitEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
public class LoginController {
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
        Map<String, Object> map = new HashMap<>();
        map.put("roles", authorization.contains("admin") ? Arrays.asList("admin", "editor") : List.of("editor"));
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
