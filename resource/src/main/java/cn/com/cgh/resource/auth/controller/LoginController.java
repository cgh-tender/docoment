package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.romantic.config.aspect.annotation.RequestLock;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.util.ResponseImpl;
import cn.com.cgh.resource.auth.service.ITbCfgRoleService;
import cn.com.cgh.resource.auth.service.ITbCfgUserService;
import cn.com.cgh.romantic.pojo.MsgPojo;
import cn.com.cgh.romantic.pojo.oasis.TbControllerLog;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.romantic.util.IdWork;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.SendQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static cn.com.cgh.romantic.constant.RomanticConstant.JWT_TOKEN_HEADER;

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
    @RequestLock
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
    public Map info(ServerHttpRequest request) {
        String authorization = request.getHeaders().getFirst(JWT_TOKEN_HEADER);
        Long userId = jwtTokenUtil.getUserIdFromToken(authorization);
        String username = jwtTokenUtil.getUserNameFromToken(authorization);
        Set<Long> data = iTbCfgRoleService.queryUserRoles(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("roles", data);
        map.put("username", username);
        map.put("userId", userId);
        return map;
    }
    @GetMapping("/hello")
    @RequestLock
    public String hello() {
        return "hello";
    }
    @GetMapping("/hello1")
    public ResponseImpl<String> hello1() {
        return new ResponseImpl<String>().setData("hello1").success();
    }
    @GetMapping("/hello2")
    public Mono<String> hello2() {
        return Mono.just("hello2");
    }
    @GetMapping("/hello3")
    public Mono<ResponseImpl<String>> hello3() {
        return Mono.just(new ResponseImpl<String>().setData("hello3").success());
    }

    @Autowired
    private SendQueue sendQueue;

    @Autowired
    private IdWork idWork;
    @GetMapping("/send")
    public String senQuery() {
        sendQueue.doSendControllerQueue(new MsgPojo<TbControllerLog>().setId(idWork.nextId()).setMsg(
                new TbControllerLog()
                        .setHttpMethod(HttpMethod.GET)
                        .setRequestUrl("/send")
                        .setClientIp("127.0.0.1")
                        .setUserAgent("admin")
        ));
        return "成功";
    }
}
