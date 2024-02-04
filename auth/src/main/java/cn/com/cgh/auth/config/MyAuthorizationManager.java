package cn.com.cgh.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

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
        return authentication.flatMap(a -> {
            Collection<? extends GrantedAuthority> authorities = a.getAuthorities();
            Map<String, Object> variables = context.getVariables();
            ServerHttpRequest request = context.getExchange().getRequest();
            String path = request.getURI().getPath();
            if (path.contains("/controllerCheckAuth")) {
                log.info(String.valueOf(request.getQueryParams().get("url")));
                log.info(String.valueOf(request.getQueryParams().get("httpMethod")));
            }
            return Mono.just(new AuthorizationDecision(true));
        });
    }
}
