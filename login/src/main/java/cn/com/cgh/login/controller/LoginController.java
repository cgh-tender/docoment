package cn.com.cgh.login.controller;

import cn.com.cgh.romantic.pojo.UserDto;
import cn.hutool.captcha.*;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
public class LoginController {
    private Integer width = 100;
    private Integer height = 40;
    private Integer codeCount = 4;
    private String randomMsg = "0123456789";
    private String[] fontNames = {"Arial", "Times New Roman", "Verdana", "Courier New"};
    private String[] fontColors = {"#000000", "#000000", "#000000", "#000000"};
    private String[] fontSizes = {"15", "15", "15", "15"};

    @Autowired
    private MenuController menuController;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate<String,Object> redisTemplateSO;
    public String getId(){
        return UUID.randomUUID() + "";
    }
    /**
     * Gif验证码类
     */
    @GetMapping("/getCode.gif")
//    @SentinelResource(value = cn.com.cgh.romantic.config.Constants.ONE_RULE
//            ,blockHandler = "handler",blockHandlerClass = CustomerBlockHandler.class
//            ,fallback = "getCodeFallback"
//    )
    public void getCodeGif(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(width, height, codeCount);
        gifCaptcha.setGenerator(new RandomGenerator(randomMsg,codeCount));
        gifCaptcha.createCode();
        String id = getId();
        redisTemplateSO.opsForValue().set(id,gifCaptcha.getCode(),60, TimeUnit.SECONDS);
        response.setHeader("uuid",id);
        response.getOutputStream().write(gifCaptcha.getImageBytes());
    }

    /**
     * 圆圈干扰验证码
     */
    @GetMapping("/getCode.circle")
//    @SentinelResource(value = cn.com.cgh.romantic.config.Constants.ONE_RULE
//            ,blockHandler = "handler",blockHandlerClass = CustomerBlockHandler.class
//            ,fallback = "getCodeFallback"
//    )
    public void getCodeJpg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(width, height, codeCount, 50);
        circleCaptcha.setGenerator(new RandomGenerator(randomMsg,codeCount));
        circleCaptcha.createCode();
        String id = getId();
        redisTemplateSO.opsForValue().set(id,circleCaptcha.getCode(),60, TimeUnit.SECONDS);
        response.setHeader("uuid",id);
        response.getOutputStream().write(circleCaptcha.getImageBytes());
    }

    /**
     * 使用干扰线方式生成的图形验证码
     */
    @GetMapping("/getCode.line")
//    @SentinelResource(value = cn.com.cgh.romantic.config.Constants.ONE_RULE
//            ,blockHandler = "handler",blockHandlerClass = CustomerBlockHandler.class
//            ,fallback = "getCodeFallback"
//    )
    public void getCodeJpg1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, codeCount, 50);
        lineCaptcha.setGenerator(new RandomGenerator(randomMsg,codeCount));
        lineCaptcha.createCode();
        String id = getId();
        redisTemplateSO.opsForValue().set(id,lineCaptcha.getCode(),60, TimeUnit.SECONDS);
        response.setHeader("uuid",id);
        response.getOutputStream().write(lineCaptcha.getImageBytes());
    }


    /**
     * 扭曲干扰验证码
     */
    @GetMapping("/getCode.shear")
//    @SentinelResource(value = cn.com.cgh.romantic.config.Constants.ONE_RULE
//            ,blockHandler = "handler",blockHandlerClass = CustomerBlockHandler.class
//            ,fallback = "getCodeFallback"
//    )
    public void getCodeJpg2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(width, height, codeCount, 3);
        shearCaptcha.setGenerator(new RandomGenerator(randomMsg,codeCount));
        shearCaptcha.createCode();
        String id = getId();
        redisTemplateSO.opsForValue().set(id,shearCaptcha.getCode(),60, TimeUnit.SECONDS);
        response.setHeader("uuid",id);
        response.getOutputStream().write(shearCaptcha.getImageBytes());
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
    public Map login(@RequestBody UserDto user){
        Map<String, String> map = new HashMap<>();
        map.put("token",user.getUsername());
        return map;
    }

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
        userDto.setPassword(passwordEncoder.encode("12345678"));
        userDto.setStatus(1);
        log.info(JSONUtil.toJsonStr(userDto));
        return userDto;
    }

}
