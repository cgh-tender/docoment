package cn.com.cgh.auth.handler;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登录成功");
        Map<String, String> map = new HashMap<>();
        TbCfgUser securityUser = (TbCfgUser) authentication.getPrincipal();
        map.put("token", securityUser.getUsername());
        response.getWriter().write(ResponseImpl.builder().message("登录成功").data(map).build().SUCCESS().toString());
    }
}
