package cn.com.cgh.gateway.filter;

import cn.com.cgh.gateway.config.Properties;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.KeyConstant;
import cn.hutool.http.Method;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static cn.com.cgh.romantic.constant.RomanticConstant.JWT_TOKEN_HEADER;
import static cn.com.cgh.romantic.constant.RomanticConstant.X_REAL_IP;
import static cn.com.cgh.romantic.util.JwtTokenUtil.USER_ID;

@Component
@Slf4j
public class GlobalIpFilter implements GlobalFilter {
    private final AntPathMatcher matcher =new AntPathMatcher();
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private Properties properties;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String hostString = request.getRemoteAddress().getHostString();
        ServerHttpRequest.Builder builder = request.mutate()
                //将获取的真实ip存入header微服务方便获取
                .header("X-Real-IP", request.getRemoteAddress().getHostString());
        if (request.getMethod().equals(Method.OPTIONS)){
            return chain.filter(exchange);
        }

        String url = request.getURI().getPath();
        String[] split = properties.getAllPermissionPath().split(",");
        Boolean allPermission = Boolean.FALSE;
        for (String path : split) {
            String[] split1 = path.split("\s");
            if (split1.length > 1){
                if (matcher.match(split1[1], url)){
                    allPermission = Boolean.TRUE;
                    break;
                }
            }else if(matcher.match(split1[0], url)){
                allPermission = Boolean.TRUE;
                break;
            }
        }
        if (allPermission){
            return chain.filter(exchange);
        }

        String token = getToken(request);
        Assert.notNull(token,"请求异常未携带token。");

        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        String username = jwtTokenUtil.getUserNameFromToken(token);
        Assert.notNull(userId,"无权访问。");

        Assert.isTrue(jwtTokenUtil.exists(username),"登录超时。");

        builder.header(USER_ID, String.valueOf(userId)).build();

        if (!JWTUtil.verify(token, JWTSignerUtil.createSigner(KeyConstant.CONTENT_INSTANCE, JwtTokenUtil.RSA_RSA.getPublicKey()))){
            throw new RuntimeException("token验证失败");
        }
        jwtTokenUtil.flushTimeout(username);
        log.info("GlobalIpFilter: {}", hostString);
        return chain.filter(exchange.mutate().request(request).build());
    }

    private String getToken(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        // 从请求头获取token
        String token = headers.getFirst(JWT_TOKEN_HEADER);
        if (StringUtils.isBlank(token)) {
            // 请求头无token则从url获取token
            token = request.getQueryParams().getFirst(JWT_TOKEN_HEADER);
        }
        if (StringUtils.isBlank(token)) {
            // 请求头和url都没有token则从cookies获取
            HttpCookie cookie = request.getCookies().getFirst(JWT_TOKEN_HEADER);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
        return jwtTokenUtil.token(token);
    }
}
