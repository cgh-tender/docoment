package cn.com.cgh.auth.handler;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.util.Application;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

import static cn.com.cgh.romantic.constant.RomanticConstant.JWT_TOKEN_HEADER;

@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    private JwtTokenUtil jwtTokenUtil;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (jwtTokenUtil == null){
            jwtTokenUtil = Application.getBean(JwtTokenUtil.class);
        }
        String token = jwtTokenUtil.token(request.getHeader(JWT_TOKEN_HEADER));
        String username = jwtTokenUtil.getUserNameFromToken(token);
        String id = jwtTokenUtil.getIdFromToken(token);
        boolean b = jwtTokenUtil.removeToken(username, id);
        log.info("退出 {}", b ? "成功" : "失败");
        response.getWriter().write(ResponseImpl.builder().message("退出成功").build().SUCCESS().toString());
    }
}
