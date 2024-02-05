package cn.com.cgh.auth.config;

import cn.hutool.jwt.JWTPayload;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

/**
 * 实现权限控制。
 *
 * @author cgh
 */
@Component
@Slf4j
public class MyAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        log.info("check Manager");
        if ("/controllerCheckAuth".equals(context.getExchange().getRequest().getURI().getPath())){
            MultiValueMap<String, String> queryParams = context.getExchange().getRequest().getQueryParams();
            String userId = context.getExchange().getRequest().getHeaders().getFirst(JWTPayload.AUDIENCE);
            String username = context.getExchange().getRequest().getHeaders().getFirst(JWTPayload.SUBJECT);
            if (StringUtils.isNotEmpty(userId)){
                log.info("check true");
                return Mono.just(new AuthorizationDecision(true));
            }
            return Mono.just(new AuthorizationDecision(false));
        }
        return Mono.just(new AuthorizationDecision(true));
    }
}
