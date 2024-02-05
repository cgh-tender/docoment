package cn.com.cgh.auth.controller;

import cn.com.cgh.romantic.pojo.auth.AuthCheckEntity;
import cn.com.cgh.romantic.server.resource.IResourceErrorController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author cgh
 */
@RestController
@RequestMapping()
@Tag(name = "AuthController", description = "认证中心登录认证")
@Slf4j
public class AuthController {
    @Autowired
    private IResourceErrorController iResourceErrorController;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @PostMapping("/login")
    public Map doLogin() {
        return new HashMap();
    }

    /**
     * 接口权限
     */
    @PostMapping("/controllerCheckAuth")
    public Mono<Boolean> controllerCheckAuth(AuthCheckEntity authCheckEntity) {
        /**
         * 接口权限
         */
        String url = authCheckEntity.getUrl();
        String httpMethod = authCheckEntity.getHttpMethod();
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> false,threadPoolTaskExecutor))
                .flatMap(resource -> Mono.just(resource));
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
