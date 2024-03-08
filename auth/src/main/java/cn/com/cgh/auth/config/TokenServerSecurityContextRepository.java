package cn.com.cgh.auth.config;

import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.hutool.jwt.JWTPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
@Component
@Slf4j
public class TokenServerSecurityContextRepository implements ServerSecurityContextRepository {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        log.info("TokenServerSecurityContextRepository save ");
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("TokenServerSecurityContextRepository load url = {}", request.getURI().getPath());
        log.info("TokenServerSecurityContextRepository load token = {}", token);
        log.info("TokenServerSecurityContextRepository load userid = {}", request.getHeaders().getFirst(JWTPayload.AUDIENCE));
        log.info("TokenServerSecurityContextRepository load username = {}", request.getHeaders().getFirst(JWTPayload.SUBJECT));
        if (token != null) {
            try {
                Long userIdFromToken = jwtTokenUtil.getUserIdFromToken(token);
                String userNameFromToken = jwtTokenUtil.getUserNameFromToken(token);
                SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
                Authentication authentication = new UsernamePasswordAuthenticationToken(userNameFromToken, null, null);
                emptyContext.setAuthentication(authentication);
                jwtTokenUtil.flushTimeout(token);
                return Mono.just(emptyContext);
            } catch (Exception e) {
                return Mono.empty();
            }
        }
        return Mono.empty();
    }
}
