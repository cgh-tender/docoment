package cn.com.cgh.login.controller;

import cn.com.cgh.core.config.Constants;
import cn.com.cgh.login.pojo.User;
import cn.com.cgh.romantic.login.ILoginController;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
public class LoginController implements ILoginController {
    /**
     * 获取 验证码
     * @return
     */
    @Override
    @GetMapping("/getCode")
    @Cacheable(cacheNames = "login", cacheManager = Constants.REDIS_CACHE_MANAGER_NAME, key = "#code")
    public Map getCode(String code){
        Map<String,String> map = new HashMap();
        log.info("login");
        map.put("url","http://dummyimage.com/100x40/dcdfe6/000000.png&text="+code);
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
