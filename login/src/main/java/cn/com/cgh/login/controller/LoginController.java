package cn.com.cgh.login.controller;

import cn.com.cgh.core.config.Application;
import cn.com.cgh.login.pojo.User;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Slf4j
@RefreshScope
public class LoginController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisTemplate stringRedisTemplate;
    /**
     * 获取 验证码
     * @return
     */
    @GetMapping("/getCode")
    public Map getCode(){
        redisTemplate.opsForValue().set("a","b");
        log.info(Objects.requireNonNull(redisTemplate.opsForValue().get("a")).toString());
        log.info(Objects.requireNonNull(redisTemplate.opsForValue().get("a")).toString());
//        redisTemplate.delete("a");
        Map<String,String> map = new HashMap();
        log.info("login");
        map.put("url","http://dummyimage.com/100x40/dcdfe6/000000.png&text=1");
        return map;
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Map login(@RequestBody User user){
        Map<String, String> map = new HashMap<>();
        map.put("token",user.getUsername());
        return map;    }

    /**
     * user info
     * @return
     */
    @GetMapping("/info")
    public Map info(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        Map<String, Object> map = new HashMap<>();
        map.put("roles", authorization.contains("admin") ? Arrays.asList("admin","editor") : List.of("editor"));
        return map;
    }

}
