package cn.com.cgh.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
@Component
@Slf4j
public class LoginServerSecurityContextRepository implements ServerSecurityContextRepository {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        log.info("TokenServerSecurityContextRepository save ");
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        if (request.getURI().getPath().equals("/login")){
            Authentication authentication = new UsernamePasswordAuthenticationToken(request.getQueryParams().getFirst("username"),
                    passwordEncoder.encode(request.getQueryParams().getFirst("password"))
                    , null);
            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            emptyContext.setAuthentication(authentication);
            return Mono.just(emptyContext);
        }
        return Mono.empty();
    }
}
