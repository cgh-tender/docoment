package cn.com.cgh.auth.config;

import cn.com.cgh.auth.handler.LoginFailureHandler;
import cn.com.cgh.romantic.constant.RomanticConstant;
import cn.com.cgh.romantic.exception.ServiceException;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.RequestUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static cn.com.cgh.romantic.constant.RomanticConstant.THREAD_LOCAL_LOG_ID;
import static cn.com.cgh.romantic.util.RequestUtil.CACHED_REQUEST_OBJECT_BODY_KEY;

/**
 * @author cgh
 */
@Component
@Slf4j
public class LoginServerSecurityContextRepository implements ServerSecurityContextRepository {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private LoginFailureHandler loginFailureHandler;


    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        log.info("LoginServerSecurityContextRepository save");
        return RequestUtil.getBody(exchange).flatMap(c -> {
            String uuid = c.getRequest().getHeaders().getFirst(RomanticConstant.UUID);
            assert uuid != null;
            String uuidCacheCode = String.valueOf(redisTemplate.opsForValue().get(uuid));
            Assert.notNull(uuidCacheCode, "11009");
            String attribute = c.getAttribute(CACHED_REQUEST_OBJECT_BODY_KEY);
            TbCfgUser bean = JSONUtil.parseObj(attribute).toBean(TbCfgUser.class);
            String code = bean.getCode();
            if (!StringUtils.equals(uuidCacheCode, code) || StringUtils.isBlank(code)) {
                String id = c.getRequest().getHeaders().getFirst(THREAD_LOCAL_LOG_ID);
                String username = bean.getUsername();
                jwtTokenUtil.removeToken(username, id, 0);
                redisTemplate.delete(uuid);
                return loginFailureHandler.sendLoginFailure(c, new ServiceException(11008));
            }
            return Mono.empty();
        });
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        log.info("LoginServerSecurityContextRepository load ");
        return Mono.empty();
    }
}
