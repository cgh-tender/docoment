package cn.com.cgh.auth.handler;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.util.Application;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.SendQueue;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Slf4j
public class FailureHandler implements AuthenticationFailureHandler {
    private JwtTokenUtil jwtTokenUtil;
    private SendQueue sendQueue;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (jwtTokenUtil == null){
            jwtTokenUtil = Application.getBean(JwtTokenUtil.class);
            sendQueue = Application.getBean(SendQueue.class);
        }
        log.error("登录失败");
        log.error(exception.getMessage());
        response.getWriter().write(ResponseImpl.builder().message(exception.getMessage()).build().FULL().toString());
    }
}
