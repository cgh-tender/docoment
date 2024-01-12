package cn.com.cgh.login.controller;

import cn.com.cgh.login.pojo.User;
import cn.com.cgh.romantic.pojo.UserDto;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


@RestController
@Slf4j
public class LoginController {
    @Autowired
    private MenuController menuController;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 获取 验证码
     * @return
     */
    @GetMapping("/getCode")
//    @Cacheable(cacheNames = "login", cacheManager = Constants.REDIS_CACHE_MANAGER_NAME, key = "#code")
    @SentinelResource(value = cn.com.cgh.romantic.config.Constants.ONE_RULE
            ,blockHandler = "handler",blockHandlerClass = CustomerBlockHandler.class
            ,fallback = "getCodeFallback"
    )
    public void getCode(HttpServletResponse response) throws IOException {
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(200, 50);
        gifCaptcha.setGenerator(new RandomGenerator("0123456789",4));
        gifCaptcha.createCode();
        response.getOutputStream().write(gifCaptcha.getImageBytes());
    }

    @Async
    public CompletableFuture ansyCall() {
        return CompletableFuture.supplyAsync(() -> {
            Map call = menuController.call();
            log.debug(JSONUtil.toJsonStr(call));
            return null;
        }).whenComplete((key, value) ->{
            log.info("key:{} value:{}",key,value);
        });
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

    @GetMapping("/loadByUsername/{username}")
    public UserDto loadByUsername(@PathVariable String username){
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(passwordEncoder.encode("password"));
        userDto.setStatus(1);
        log.info(JSONUtil.toJsonStr(userDto));
        return userDto;
    }

}
