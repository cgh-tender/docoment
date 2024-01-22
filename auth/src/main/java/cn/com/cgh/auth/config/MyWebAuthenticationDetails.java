package cn.com.cgh.auth.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Getter
public class MyWebAuthenticationDetails extends WebAuthenticationDetails {
    // 验不通过 为 true
    private Boolean verificationCode = Boolean.FALSE;
    // 验证码是否丢失
    private Boolean verificationCodeLose = Boolean.FALSE;

    public MyWebAuthenticationDetails(HttpServletRequest request,RedisTemplate<String, Object> redisTemplateSO) {
        super(request);
        String uuid = request.getHeader("uuid");
        String code = request.getParameter("code");
        String cacheCode = (String) redisTemplateSO.opsForValue().get(uuid);
        if (StringUtils.isBlank(cacheCode)){
            verificationCodeLose = true;
        }else if (!StringUtils.equalsIgnoreCase(cacheCode, code)) {
            verificationCode = true;
        }
    }

    public MyWebAuthenticationDetails(String remoteAddress, String sessionId) {
        super(remoteAddress, sessionId);
    }
}
