package cn.com.cgh.auth.handler;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.util.Application;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static cn.com.cgh.romantic.constant.RomanticConstant.JWT_TOKEN_HEADER;

@Slf4j
public class MyLogoutSuccessHandler implements ServerLogoutSuccessHandler {
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        if (jwtTokenUtil == null){
            jwtTokenUtil = Application.getBean(JwtTokenUtil.class);
        }
        ServerWebExchange exchange1 = exchange.getExchange();
        ServerHttpResponse response = exchange1.getResponse();
        ServerHttpRequest request = exchange1.getRequest();
        String token = jwtTokenUtil.token(request.getHeaders().getFirst(JWT_TOKEN_HEADER));
        String username = jwtTokenUtil.getUserNameFromToken(token);
        String id = jwtTokenUtil.getIdFromToken(token);
        boolean b = jwtTokenUtil.removeToken(username, id);
        log.info("退出 {}", b ? "成功" : "失败");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(
                JSON.toJSONBytes(ResponseImpl.builder().message("退出成功").build().SUCCESS())
        )));
    }
}
