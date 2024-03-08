package cn.com.cgh.auth.filter;

import cn.com.cgh.romantic.em.LoginStatus;
import cn.com.cgh.romantic.pojo.MsgPojo;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.romantic.util.Application;
import cn.com.cgh.romantic.util.IdWork;
import cn.com.cgh.romantic.util.RequestUtil;
import cn.com.cgh.romantic.util.SendQueue;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Date;

import static cn.com.cgh.romantic.constant.RomanticConstant.*;
import static cn.com.cgh.romantic.util.RequestUtil.CACHED_REQUEST_OBJECT_BODY_KEY;

/**
 * @author cgh
 */
@Slf4j
@Component
public class SendLogFilter implements WebFilter {
    private SendQueue sendQueue;
    private IdWork idWork;

    @Override
    public @NotNull Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (sendQueue == null) {
            sendQueue = Application.getBean(SendQueue.class);
        }
        if (idWork == null) {
            idWork = Application.getBean(IdWork.class);
        }
        ServerHttpRequest request = exchange.getRequest();
        if (request.getURI().getPath().contains("/login")) {
            return RequestUtil.getBody(exchange).flatMap(c -> {
                String attribute = c.getAttribute(CACHED_REQUEST_OBJECT_BODY_KEY);
                TbCfgUser tbCfgUser = JSONUtil.parseObj(attribute).toBean(TbCfgUser.class);
                long id = idWork.nextId();
                ServerHttpRequest.Builder mutate = request.mutate();
                mutate.header(THREAD_LOCAL_LOG_ID, id + "");
                String userAgent = request.getHeaders().getFirst(USER_AGENT);
                UserAgent parse = UserAgentUtil.parse(request.getHeaders().getFirst(USER_AGENT));
                TbLoginLog loginLog = TbLoginLog.builder()
                        .username(tbCfgUser.getUsername())
                        .userId(tbCfgUser.getId() == null ? 0 : tbCfgUser.getId())
                        .clientIp(request.getHeaders().getFirst(X_REAL_IP))
                        .userAgent(userAgent)
                        .browser(parse.getBrowser().getName() + "/" + parse.getVersion())
                        .mobile(parse.isMobile())
                        .engine(parse.getEngine().getName() + "/" + parse.getEngineVersion())
                        .osSys(parse.getPlatform().getName() + "/" + parse.getOs().getName() + "/" + parse.getOsVersion())
                        .loginStatus(LoginStatus.IN)
                        .build();
                loginLog.setId(id);
                loginLog.setCreateTime(new Date());
                MsgPojo<Object> build = MsgPojo.builder().id(id).msg(
                        loginLog
                ).build();
                log.info(JSONUtil.toJsonStr(build));
                sendQueue.doSendLoginQueue(build);
                return chain.filter(exchange.mutate().request(mutate.build()).build());
            });
        }
        return chain.filter(exchange);
    }
}
