package cn.com.cgh.auth.filter;

import cn.com.cgh.romantic.em.LoginStatus;
import cn.com.cgh.romantic.pojo.MsgPojo;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.util.Application;
import cn.com.cgh.romantic.util.IdWork;
import cn.com.cgh.romantic.util.SendQueue;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * @author cgh
 */
@Slf4j
public class SendLogFilter implements WebFilter {
    private SendQueue sendQueue;
    private IdWork idWork;
    public static final ThreadLocal<Long> THREAD_LOCAL_LOG_ID = new ThreadLocal<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        if (sendQueue == null) {
            sendQueue = Application.getBean(SendQueue.class);
        }
        if (idWork == null) {
            idWork = Application.getBean(IdWork.class);
        }
        ServerHttpRequest request = exchange.getRequest();
        log.info("start {}", request.getURI().getPath());
        if (request.getURI().getPath().contains("/doLogin")){
            long id = idWork.nextId();
            log.info(id + "");
            THREAD_LOCAL_LOG_ID.set(id);
            TbLoginLog loginLog = TbLoginLog.builder()
                    .username(request.getQueryParams().getFirst("username"))
                    .userId(0L)
                    .loginStatus(LoginStatus.IN)
                    .build();
            loginLog.setId(id);
            loginLog.setCreateTime(new Date());
            MsgPojo<Object> build = MsgPojo.builder().id(id).msg(
                    loginLog
            ).build();
            log.info(JSONUtil.toJsonStr(build));
            sendQueue.doSendLoginQueue(build);
        }
        try {
            return chain.filter(exchange);
        } finally {
            THREAD_LOCAL_LOG_ID.remove();
        }
    }

    private RedisTemplate<String, Object> redisTemplateSO;
}
