package cn.com.cgh.auth.filter;

import cn.com.cgh.auth.pojo.SecurityUser;
import cn.com.cgh.core.util.RequestWrapper;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import java.util.Objects;

@Slf4j
public class MyFilter extends UsernamePasswordAuthenticationFilter {
    public MyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            RequestWrapper requestWrapper = null;
            SecurityUser user = null;
            if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())
                    || "application/json;charset=UTF-8".equals(request.getContentType())){
                    requestWrapper = new RequestWrapper(request);
                    String body = requestWrapper.getBody();
                    if (StringUtils.isEmpty(body)){
                        log.info("请求体为空");
                    }
                user = JSONUtil.parseObj(body).toBean(SecurityUser.class);
            }else{
                user = new SecurityUser();
                user.setUsername(request.getParameter("username"));
                user.setPassword(request.getParameter("password"));
            }
            Assert.notNull(user, "SecurityUser can not be null");
            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(user.getUsername(), user.getPassword());
            this.setDetails(Objects.requireNonNullElse(requestWrapper, request), authRequest);
            Authentication authenticate = this.getAuthenticationManager().authenticate(authRequest);
            log.info(JSONUtil.toJsonStr(authenticate));
            return authenticate;
        }
    }
}
