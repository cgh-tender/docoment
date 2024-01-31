package cn.com.cgh.auth.filter;

import cn.com.cgh.romantic.em.LoginStatus;
import cn.com.cgh.romantic.pojo.MsgPojo;
import cn.com.cgh.romantic.pojo.oasis.TbLoginLog;
import cn.com.cgh.romantic.util.Application;
import cn.com.cgh.romantic.util.IdWork;
import cn.com.cgh.romantic.util.SendQueue;
import cn.hutool.json.JSONUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

/**
 * @author cgh
 */
@Slf4j
public class SendLogFilter extends OncePerRequestFilter {
    private SendQueue sendQueue;
    private IdWork idWork;
    public static final ThreadLocal<Long> THREAD_LOCAL_LOG_ID = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (sendQueue == null) {
            sendQueue = Application.getBean(SendQueue.class);
        }
        if (idWork == null) {
            idWork = Application.getBean(IdWork.class);
        }
        log.info("start {}", request.getRequestURI());
        long id = idWork.nextId();
        log.info(id + "");
        THREAD_LOCAL_LOG_ID.set(id);
        TbLoginLog loginLog = TbLoginLog.builder()
                .username(request.getParameter("username"))
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
        try {
            filterChain.doFilter(request, response);
        } finally {
            THREAD_LOCAL_LOG_ID.remove();
        }
    }

    private RedisTemplate<String, Object> redisTemplateSO;
}
