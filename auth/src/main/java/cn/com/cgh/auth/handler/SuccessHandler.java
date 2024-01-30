package cn.com.cgh.auth.handler;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.em.LoginStatus;
import cn.com.cgh.romantic.pojo.MsgPojo;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.romantic.util.Application;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.SendQueue;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;

import static cn.com.cgh.romantic.constant.RomanticConstant.X_REAL_IP;

@Slf4j
public class SuccessHandler implements AuthenticationSuccessHandler {
    private JwtTokenUtil jwtTokenUtil;
    private SendQueue sendQueue;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (jwtTokenUtil == null){
            jwtTokenUtil = Application.getBean(JwtTokenUtil.class);
            sendQueue = Application.getBean(SendQueue.class);
        }
        log.info("登录成功");
        TbCfgUser securityUser = (TbCfgUser) authentication.getPrincipal();
        Map<String, Object> map = jwtTokenUtil.generateTokenAndRefreshToken(securityUser.getId(), securityUser.getUsername());
        MsgPojo<Object> build = MsgPojo.builder().id(securityUser.getId()).msg(
                TbLoginLog.builder()
                        .username(securityUser.getUsername())
                        .userId(securityUser.getId())
                        .clientIp(request.getHeader(X_REAL_IP))
                        .loginStatus(LoginStatus.SUCCESS)
                        .userAgent(String.valueOf(map.get(JwtTokenUtil.ACCESS_TOKEN)))
                        .build()
        ).build();
        sendQueue.doSendLoginQueue(build);
        response.getWriter().write(ResponseImpl.builder().message("登录成功").data(map).build().SUCCESS().toString());
    }
}
