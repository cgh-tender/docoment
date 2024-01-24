package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.core.util.Constants;
import cn.com.cgh.core.util.IdWork;
import cn.hutool.captcha.*;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/image")
@Slf4j
public class ImageCodeController {
    @Autowired
    private TbCfgResourceController resourceController;
    @Autowired
    private RedisTemplate<String,Object> redisTemplateSO;
    @Autowired
    private IdWork idWork;
    private Integer width = 100;
    private Integer height = 40;
    private Integer codeCount = 4;
    private String randomMsg = "0123456789";
    private String[] fontNames = {"Arial", "Times New Roman", "Verdana", "Courier New"};
    private String[] fontColors = {"#000000", "#000000", "#000000", "#000000"};
    private String[] fontSizes = {"15", "15", "15", "15"};
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
        query(response, gifCaptcha);
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
        query(response, circleCaptcha);
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
        AbstractCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, codeCount, 50);
        query(response, lineCaptcha);
    }

    private void query(HttpServletResponse response, AbstractCaptcha abstractCaptcha) throws IOException {
        abstractCaptcha.setGenerator(new RandomGenerator(randomMsg, codeCount));
        abstractCaptcha.createCode();
        String id = getId();
        redisTemplateSO.opsForValue().set(id, abstractCaptcha.getCode(), 60, TimeUnit.SECONDS);
        response.setHeader(Constants.UUID, id);
        response.getOutputStream().write(abstractCaptcha.getImageBytes());
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
        query(response, shearCaptcha);
    }

    @Async
    public CompletableFuture ansyCall() {
        return CompletableFuture.supplyAsync(() -> {
            Map call = resourceController.call();
            log.debug(JSONUtil.toJsonStr(call));
            return null;
        }).whenComplete((key, value) -> {
            log.info("key:{} value:{}", key, value);
        });
    }

    public String getId() {
        return idWork.nextId()+"";
    }
}
