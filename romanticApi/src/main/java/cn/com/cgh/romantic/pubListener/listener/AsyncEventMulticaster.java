package cn.com.cgh.romantic.pubListener.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 发送事件请求
 * @author cgh
 */
@Slf4j
public class AsyncEventMulticaster extends SimpleApplicationEventMulticaster {
    @Autowired
    public AsyncEventMulticaster(ThreadPoolTaskExecutor threadPoolTaskExecutor){
        log.info("设置广播器的TaskExecutor>>>>>>>>>>>>>>>>>>>>>");
        setTaskExecutor(threadPoolTaskExecutor);
    }
}
