package cn.com.cgh.auth.config;

import cn.com.cgh.romantic.util.RequestUtil;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static cn.com.cgh.romantic.util.RequestUtil.CACHED_REQUEST_OBJECT_BODY_KEY;

/**
 * @author cgh
 */
@Component
@Slf4j
public class MyServerAuthenticationConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return RequestUtil.getBody(exchange).flatMap(c -> {
            String attribute = c.getAttribute(CACHED_REQUEST_OBJECT_BODY_KEY);
            TbCfgUser bean = JSONUtil.parseObj(attribute).toBean(TbCfgUser.class);
            return Mono.just((Authentication)UsernamePasswordAuthenticationToken.unauthenticated(bean.getUsername(), bean.getPassword()));
        }).doOnError(RuntimeException::new);
    }
}
