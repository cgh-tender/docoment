package cn.com.cgh.gateway.filter;

import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.KeyConstant;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

import static cn.com.cgh.romantic.constant.RomanticConstant.JWT_TOKEN_HEADER;
import static cn.com.cgh.romantic.constant.RomanticConstant.X_REAL_IP;

@Component
@Slf4j
public class GlobalIpFilter implements GlobalFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate()
                //将获取的真实ip存入header微服务方便获取
                .header("X-Real-IP", exchange.getRequest().getRemoteAddress().getHostString())
//                .header("Content-Type","application/json;charset=UTF-8")
                .build();
        List<String> tokens = request.getHeaders().get(JWT_TOKEN_HEADER);
        Assert.notEmpty(tokens,"请求异常未携带token。");
        String token = jwtTokenUtil.token(request.getHeaders().get(JWT_TOKEN_HEADER).get(0));
        if (!JWTUtil.verify(token, JWTSignerUtil.createSigner(KeyConstant.CONTENT_INSTANCE, JwtTokenUtil.RSA_RSA.getPublicKey()))){
            throw new RuntimeException("token验证失败");
        }
        log.info("GlobalIpFilter: " + ((HttpServletRequest) exchange.getRequest()).getHeaders(X_REAL_IP));
        return chain.filter(exchange.mutate().request(request).build());
    }
}
