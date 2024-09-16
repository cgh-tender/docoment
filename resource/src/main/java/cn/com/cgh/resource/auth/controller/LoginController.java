package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.romantic.config.aspect.annotation.RequestLock;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.server.auth.IAuthCheckController;
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
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static cn.com.cgh.romantic.constant.RomanticConstant.*;

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
    @Autowired
    private IAuthCheckController iAuthCheckController;
    /**
     * user info
     *
     * @return
     */
    @GetMapping("/info")
    public Mono<ResponseImpl<Map>> info(ServerHttpRequest request) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> iAuthCheckController.test())).map(t ->
        {
            System.out.println(t);
            Long userId = Long.valueOf(Objects.requireNonNull(request.getHeaders().getFirst(JWT_USER_CERTIFIED_ID)));
            String username = request.getHeaders().getFirst(JWT_USER_CERTIFIED_NAME);
            Set<Long> data = iTbCfgRoleService.queryUserRoles(userId);
            Map<String, Object> map = new HashMap<>();
            map.put("roles", data);
            map.put("username", username);
            map.put("userId", userId);
            return ResponseImpl.ok(map);
        });
    }
    @GetMapping("/hello")
    @RequestLock
    public String hello() {
        return "hello";
    }
    @GetMapping("/hello1")
    public ResponseImpl<String> hello1() {
        return ResponseImpl.ok("hello1");
    }
    @GetMapping("/hello2")
    public Mono<String> hello2() {
        return Mono.just("hello2");
    }
    @GetMapping("/hello3")
    public Mono<ResponseImpl<String>> hello3() {
        return Mono.just(ResponseImpl.ok("hello3"));
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
