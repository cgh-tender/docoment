package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.romantic.constant.RomanticConstant;
import cn.com.cgh.romantic.server.auth.IAuthCheckController;
import cn.com.cgh.romantic.util.IdWork;
import cn.com.cgh.romantic.util.ResponseUtil;
import cn.hutool.captcha.*;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
    @Autowired
    private IAuthCheckController iAuthCheckController;
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
    public Mono<Void> getCodeGif(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(width, height, codeCount);
        return query(response, gifCaptcha);
    }

    /**
     * 圆圈干扰验证码
     */
    @GetMapping("/getCode.circle")
//    @SentinelResource(value = cn.com.cgh.romantic.config.Constants.ONE_RULE
//            ,blockHandler = "handler",blockHandlerClass = CustomerBlockHandler.class
//            ,fallback = "getCodeFallback"
//    )
    public Mono<Void> getCodeJpg(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(width, height, codeCount, 50);
        return query(response, circleCaptcha);
    }

    /**
     * 使用干扰线方式生成的图形验证码
     *
     * @return
     */
    @GetMapping("/getCode.line")
//    @SentinelResource(value = cn.com.cgh.romantic.config.Constants.ONE_RULE
//            ,blockHandler = "handler",blockHandlerClass = CustomerBlockHandler.class
//            ,fallback = "getCodeFallback"
//    )
    public Mono<Void> getCodeJpg1(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
        AbstractCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(width, height, codeCount, 50);
        return query(response, lineCaptcha);
    }

    private Mono<Void> query(ServerHttpResponse response, AbstractCaptcha abstractCaptcha) throws IOException {
        abstractCaptcha.setGenerator(new RandomGenerator(randomMsg, codeCount));
        abstractCaptcha.createCode();
        String id = getId();
        redisTemplateSO.opsForValue().set(id, abstractCaptcha.getCode(), 60, TimeUnit.SECONDS);
        response.getHeaders().add(RomanticConstant.UUID, id);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
        return ResponseUtil.writeResponse(response, abstractCaptcha.getImageBytes());
    }


    /**
     * 扭曲干扰验证码
     *
     * @return
     */
    @GetMapping("/getCode.shear")
//    @SentinelResource(value = cn.com.cgh.romantic.config.Constants.ONE_RULE
//            ,blockHandler = "handler",blockHandlerClass = CustomerBlockHandler.class
//            ,fallback = "getCodeFallback"
//    )
    public Mono<Void> getCodeJpg2(ServerHttpRequest request, ServerHttpResponse response) throws IOException {
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(width, height, codeCount, 3);
        return query(response, shearCaptcha);
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
