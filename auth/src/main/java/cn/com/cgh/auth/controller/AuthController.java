package cn.com.cgh.auth.controller;

import cn.com.cgh.romantic.pojo.auth.AuthCheckEntity;
import cn.com.cgh.romantic.server.resource.IResourceErrorController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

import static cn.com.cgh.romantic.constant.RomanticConstant.JWT_USER_CERTIFIED_NAME;

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

    /**
     * 接口权限
     */
    @PostMapping("/controllerCheckAuth")
    public Mono<Boolean> controllerCheckAuth(AuthCheckEntity authCheckEntity) {
        log.info("AuthController controllerCheckAuth");
        /**
         * 接口权限
         */
        String url = authCheckEntity.getUrl();
        String httpMethod = authCheckEntity.getHttpMethod();
        log.info("url {} httpMethod {}", url, httpMethod);
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> true,threadPoolTaskExecutor))
                .flatMap(Mono::just);
    }

    @GetMapping("/test")
    public Mono<String> test(ServerHttpRequest request) {
        log.info(request.getHeaders().getFirst(JWT_USER_CERTIFIED_NAME));
        return Mono.just("test");
    }
}
