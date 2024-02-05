package cn.com.cgh.auth.config;

import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.hutool.jwt.JWTPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static cn.com.cgh.core.util.Constants.UUID;

/**
 * @author cgh
 */
@Component
@Slf4j
public class TokenServerSecurityContextRepository implements ServerSecurityContextRepository {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        log.info("TokenServerSecurityContextRepository save ");
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if ("/login".equals(path)){
            String uuid = request.getHeaders().getFirst(UUID);
            String uuidCacheCode = String.valueOf(redisTemplate.opsForValue().get(uuid));
            String uuidCode = request.getQueryParams().getFirst("code");
            if (!StringUtils.equals(uuidCacheCode,uuidCode) || StringUtils.isBlank(uuidCode)){
                redisTemplate.delete(uuid);
                return Mono.empty();
            }
            return Mono.empty();
        }
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("TokenServerSecurityContextRepository load token = {}", token);
        log.info("TokenServerSecurityContextRepository load userid = {}",  request.getHeaders().getFirst(JWTPayload.AUDIENCE));
        log.info("TokenServerSecurityContextRepository load username = {}",  request.getHeaders().getFirst(JWTPayload.SUBJECT));
        if (token != null) {
            try {
                Long userIdFromToken = jwtTokenUtil.getUserIdFromToken(token);
                String userNameFromToken = jwtTokenUtil.getUserNameFromToken(token);
                SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
                Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, null);
                emptyContext.setAuthentication(authentication);
                return Mono.just(emptyContext);
            } catch (Exception e) {
                return Mono.empty();
            }
        }
        return Mono.empty();
    }
}
