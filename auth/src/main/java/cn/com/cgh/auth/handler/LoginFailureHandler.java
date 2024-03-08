package cn.com.cgh.auth.handler;

import cn.com.cgh.romantic.em.LoginStatus;
import cn.com.cgh.romantic.exception.ServiceException;
import cn.com.cgh.romantic.pojo.MsgPojo;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.util.JwtTokenUtil;
import cn.com.cgh.romantic.util.SendQueue;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

import static cn.com.cgh.romantic.constant.RomanticConstant.THREAD_LOCAL_LOG_ID;

/**
 * @author cgh
 */
@Slf4j
@Component
public class LoginFailureHandler implements ServerAuthenticationFailureHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SendQueue sendQueue;
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        return sendLoginFailure(webFilterExchange.getExchange(),exception);
    }

    public Mono<Void> sendLoginFailure(ServerWebExchange exchange, RuntimeException exception) {
        String id = exchange.getRequest().getHeaders().getFirst(THREAD_LOCAL_LOG_ID);
        return Mono.justOrEmpty(id).flatMap(i -> {
            TbLoginLog loginLog = TbLoginLog.builder()
                    .loginStatus(LoginStatus.ERROR)
                    .logoutTime(new Date())
                    .build();
            loginLog.setId(Long.valueOf(i));
            log.info(JSONUtil.toJsonStr(loginLog));
            MsgPojo<Object> build = MsgPojo.builder().id(loginLog.getId()).msg(
                    loginLog
            ).build();
            sendQueue.doSendLoginQueue(build);
            if (exception instanceof BadCredentialsException){
                return Mono.error(new ServiceException(11001));
            }
            return Mono.error(exception);
        });
    }
}
