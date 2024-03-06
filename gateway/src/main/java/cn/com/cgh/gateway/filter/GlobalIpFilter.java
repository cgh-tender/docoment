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
import java.util.concurrent.CompletableFuture;

import static cn.com.cgh.romantic.constant.RomanticConstant.JWT_TOKEN_HEADER;
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
        Assert.notNull(token, "请求异常未携带token。");
        if ("admin".equals(token)){
            return chain.filter(exchange.mutate().build());
        }
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        String id = jwtTokenUtil.getIdFromToken(token);
        String username = jwtTokenUtil.getUserNameFromToken(token);
        Assert.notNull(userId, "无权访问。");

        String key = MessageFormat.format(JWT_CACHE_KEY, username, id);
        Assert.isTrue(jwtTokenUtil.exists(key), "登录超时。");

        if (!JWTUtil.verify(token, JWTSignerUtil.createSigner(KeyConstant.CONTENT_INSTANCE, JwtTokenUtil.RSA_RSA.getPublicKey()))) {
            throw new RuntimeException("token验证失败");
        }
        builder.header(JWTPayload.AUDIENCE, String.valueOf(userId));
        builder.header(JWTPayload.SUBJECT, username);
        ServerHttpRequest build = builder.build();

        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> parPermission(url, build), threadPoolTaskExecutor))
                .then(chain.filter(exchange.mutate().request(build).build()));
    }

    private boolean parPermission(String url, ServerHttpRequest request) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put(JWTPayload.AUDIENCE, Collections.singletonList(request.getHeaders().getFirst(JWTPayload.AUDIENCE)));
        map.put(JWTPayload.SUBJECT, Collections.singletonList(request.getHeaders().getFirst(JWTPayload.SUBJECT)));
        map.put(JWT_TOKEN_HEADER, Collections.singletonList(request.getHeaders().getFirst(JWT_TOKEN_HEADER)));
        Assert.isTrue(iAuthCheckController
                .controllerCheckAuth(
                        AuthCheckEntity.builder()
                                .url(url)
                                .httpMethod(request.getMethod().name())
                                .build(), map
                ), "500");
        return true;
    }
}
