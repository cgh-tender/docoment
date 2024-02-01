package cn.com.cgh.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 实现权限控制。
 *
 * @author cgh
 */
@Component
@Slf4j
public class MyAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        Collection<? extends GrantedAuthority> collection = authentication.get().getAuthorities();
        Map<String, String> variables = context.getVariables();
        String requestURI = context.getRequest().getRequestURI();
        log.info(requestURI);
        return new AuthorizationDecision(true);
    }
}
