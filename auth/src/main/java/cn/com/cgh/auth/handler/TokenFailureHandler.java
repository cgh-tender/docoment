package cn.com.cgh.auth.handler;

import cn.com.cgh.romantic.util.ResponseImpl;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.SendQueue;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
@Slf4j
@Component
public class TokenFailureHandler implements ServerAuthenticationFailureHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SendQueue sendQueue;
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        exception.printStackTrace();
        log.error(exception.getMessage());
        String message = exception.getMessage();
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JSON.toJSONBytes(ResponseImpl.builder().message(message).build().full()))));
    }
}
