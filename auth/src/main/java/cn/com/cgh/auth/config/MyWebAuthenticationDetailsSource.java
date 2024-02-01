package cn.com.cgh.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/**
 * @author cgh
 */
public class MyWebAuthenticationDetailsSource extends WebAuthenticationDetailsSource {

    private final RedisTemplate<String, Object> redisTemplateSO;

    public MyWebAuthenticationDetailsSource(RedisTemplate<String,Object> redisTemplateSO) {
        super();
        this.redisTemplateSO = redisTemplateSO;
    }

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new MyWebAuthenticationDetails(context,redisTemplateSO);
    }
}
