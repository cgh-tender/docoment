package cn.com.cgh.auth.handler;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.util.Application;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.SendQueue;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

@Slf4j
public class FailureHandler implements ServerAuthenticationFailureHandler {
    private JwtTokenUtil jwtTokenUtil;
    private SendQueue sendQueue;
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        if (jwtTokenUtil == null){
            jwtTokenUtil = Application.getBean(JwtTokenUtil.class);
        }
        if (sendQueue == null){
            sendQueue = Application.getBean(SendQueue.class);
        }
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        exception.printStackTrace();
        log.error(exception.getMessage());
        String message = exception.getMessage();
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JSON.toJSONBytes(ResponseImpl.builder().message(message).build().FULL()))));
    }
}
