package cn.com.cgh.auth.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
@Component
@Slf4j
public class ManageAuthenticationFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        RequestUtil.getBody(exchange).flatMap(exc->{
//            String cachedRequestObjectBody = exc.getAttribute("cachedRequestObjectBody");
//            TbCfgUser bean = JSONUtil.parseObj(cachedRequestObjectBody).toBean(TbCfgUser.class);
//
//        });
//        HttpHeaders headers = exchange.getRequest().getHeaders();
//        String manageToken = headers.getFirst("MANAGE_TOKEN");
//        System.out.println(manageToken);
        return chain.filter(exchange);
    }
}
