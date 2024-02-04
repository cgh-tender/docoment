package cn.com.cgh.auth.handler;

import cn.com.cgh.romantic.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static cn.com.cgh.romantic.constant.RomanticConstant.JWT_TOKEN_HEADER;

/**
 * @author cgh
 */
@Component
@Slf4j
public class TokenLogoutHandler implements ServerLogoutHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
        ServerHttpRequest request = exchange.getExchange().getRequest();
        String token = jwtTokenUtil.token(request.getHeaders().getFirst(JWT_TOKEN_HEADER));
        String username = jwtTokenUtil.getUserNameFromToken(token);
        String id = jwtTokenUtil.getIdFromToken(token);
        boolean b = jwtTokenUtil.removeToken(username, id);
        log.info("退出 {}", b ? "成功" : "失败");
        return Mono.empty();
    }
}
