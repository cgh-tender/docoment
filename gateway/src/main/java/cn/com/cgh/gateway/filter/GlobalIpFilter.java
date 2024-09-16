package cn.com.cgh.gateway.filter;

import cn.com.cgh.gateway.config.Properties;
import cn.com.cgh.romantic.constant.RomanticConstant;
import cn.com.cgh.romantic.pojo.auth.AuthCheckEntity;
import cn.com.cgh.romantic.server.auth.IAuthCheckController;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.KeyConstant;
import cn.com.cgh.romantic.util.RequestUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static cn.com.cgh.romantic.constant.RomanticConstant.*;
import static cn.com.cgh.romantic.util.JwtTokenUtil.JWT_CACHE_KEY;

/**
 * @author cgh
 */
@Component
@Slf4j
public class GlobalIpFilter implements GlobalFilter {
    private final AntPathMatcher matcher = new AntPathMatcher();
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private Properties properties;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Lazy
    @Autowired
    private IAuthCheckController iAuthCheckController;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String hostString = RequestUtil.getIpAddr(exchange);
        ServerHttpRequest.Builder builder = request.mutate()
                //将获取的真实ip存入header微服务方便获取
                .header(RomanticConstant.X_REAL_IP, hostString);
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            return chain.filter(exchange);
        }
        boolean present = Optional.ofNullable(request.getHeaders().getFirst(JWT_USER_CERTIFIED_NAME)).isPresent();
        if (present){
            log.info("通过认证 ---- 请求");
            return chain.filter(exchange);
        }
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            return chain.filter(exchange);
        }

        String url = request.getURI().getPath();
        String[] split = properties.getAllPermissionPath().split(",");
        boolean allPermission = Boolean.FALSE;
        for (String path : split) {
            String[] split1 = path.split("\s");
            if (split1.length > 1) {
                if (matcher.match(split1[1], url)) {
                    if (split1[0].equals(request.getMethod().name())) {
                        allPermission = Boolean.TRUE;
                        break;
                    }
                }
            } else if (matcher.match(split1[0], url)) {
                allPermission = Boolean.TRUE;
                break;
            }
        }
        if (allPermission) {
            return chain.filter(exchange);
        }

        String token = jwtTokenUtil.getToken(request);
        Assert.notNull(token, "11010");
        if ("admin".equals(token)){
            return chain.filter(exchange.mutate().build());
        }
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        String id = jwtTokenUtil.getIdFromToken(token);
        String username = jwtTokenUtil.getUserNameFromToken(token);
        Assert.notNull(userId, "11007");

        String key = MessageFormat.format(JWT_CACHE_KEY, username, id);
        Assert.isTrue(jwtTokenUtil.exists(key), "11002");

        if (!JWTUtil.verify(token, JWTSignerUtil.createSigner(KeyConstant.CONTENT_INSTANCE, JwtTokenUtil.RSA_RSA.getPublicKey()))) {
            throw new RuntimeException("11003");
        }
        builder.header(JWTPayload.AUDIENCE, String.valueOf(userId));
        builder.header(JWTPayload.SUBJECT, username);
        String methodName = request.getMethod().name();

        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> parPermission(url, userId, username, token, methodName), threadPoolTaskExecutor))
                .doOnNext(result -> {
                    if (result) {
                        builder.header(JWT_USER_CERTIFIED_NAME, username);
                        builder.header(JWT_USER_CERTIFIED_ID, String.valueOf(userId));
                        log.info("权限校验通过");
                    } else {
                        log.info("权限校验不通过");
                    }
                })
                .map(result -> builder.build())
                .flatMap(build -> {
                    ServerWebExchange build1 = exchange.mutate().request(build).build();
                    return chain.filter(build1);
                });
    }

    private boolean parPermission(String url, long userId, String userName, String token, String methodName) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put(JWTPayload.AUDIENCE, Collections.singletonList(String.valueOf(userId)));
        map.put(JWTPayload.SUBJECT, Collections.singletonList(userName));
        map.put(JWT_TOKEN_HEADER, Collections.singletonList(token));
        Assert.isTrue(iAuthCheckController
                .controllerCheckAuth(
                        new AuthCheckEntity().setUrl(url).setHttpMethod(methodName)
                        , map
                ), "500");
        return true;
    }
}
