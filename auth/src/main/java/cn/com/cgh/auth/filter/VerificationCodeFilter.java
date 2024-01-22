package cn.com.cgh.auth.filter;

import cn.com.cgh.auth.exception.VerificationCodeException;
import cn.com.cgh.gallery.util.Application;
import cn.com.cgh.core.util.RequestWrapper;
import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.pojo.UserDto;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class VerificationCodeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Boolean enableVerificationCode = Application.getApplicationContext().getEnvironment().getProperty("enableVerificationCode", Boolean.class, Boolean.FALSE);
        if (!enableVerificationCode || !("/login".equals(request.getRequestURI()) && request.getMethod().equals("POST"))) {
            filterChain.doFilter(request, response);
        } else {
            try {
                if (redisTemplateSO == null) {
                    redisTemplateSO = (RedisTemplate<String, Object>) Application.getBean("redisTemplateSO");
                }
                String uuid = request.getHeader("uuid");
                String code = request.getParameter("code");
                RequestWrapper requestWrapper = null;
                if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())
                        || "application/json;charset=UTF-8".equals(request.getContentType())){
                    if (StringUtils.isEmpty(code)){
                        requestWrapper = new RequestWrapper(request);
                        String body = requestWrapper.getBody();
                        if (StringUtils.isEmpty(body)){
                            log.info("请求体为空");
                        }
                        code = JSONUtil.parseObj(body).toBean(UserDto.class).getCode();
                    }
                }
                String verificationCode = (String) redisTemplateSO.opsForValue().get(uuid);
                if (StringUtils.isBlank(verificationCode)){
                    throw new VerificationCodeException("验证码已失效");
                }else if (!StringUtils.equalsIgnoreCase(verificationCode, code)) {
                    throw new VerificationCodeException("验证码输入错误");
                }
                filterChain.doFilter(Objects.requireNonNullElse(requestWrapper, request), response);
            } catch (VerificationCodeException e) {
                response.getOutputStream().write(JSONUtil.toJsonStr(ResponseImpl.builder()
                        .message(e.getMessage())
                        .build().FULL()).getBytes());
            }
        }
    }

    private RedisTemplate<String, Object> redisTemplateSO;

    public void verificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
