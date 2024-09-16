package cn.com.cgh.romantic.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import cn.com.cgh.romantic.util.BaseCoreDelay;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author cgh
 */
@Slf4j
@Component
public class LoggerConfig implements ApplicationRunner {
    static {
        log.info("LoggerConfig:已启动");
    }
    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Autowired
    private LoginProperties logProperties;
    @Autowired
    private TaskExecutionProperties taskExecutionProperties;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    public void changeLogLevel(String loggerName, String level) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger(loggerName);
        if (logger != null) {
            logger.setLevel(Level.toLevel(level, Level.INFO));
            loggerContext.start();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        nacosConfigManager.getConfigService().addListener("core.yml","DEFAULT_GROUP",new Listener(){
            @Override
            public Executor getExecutor() {
                return null;
            }
            @Override
            public void receiveConfigInfo(String s) {
                DelayQueue<BaseCoreDelay> queue = new DelayQueue<BaseCoreDelay>();
                queue.put(new BaseCoreDelay("更新日志级别...", 3, TimeUnit.NANOSECONDS){
                    @Override
                    public void run() {
                        runFlyway();
                    }
                });
                queue.put(new BaseCoreDelay("更新线程池大小...", 3, TimeUnit.NANOSECONDS){
                    @Override
                    public void run() {
                        flushThread();
                    }
                });
                try {
                    while (!queue.isEmpty()) {
                        BaseCoreDelay task = queue.take();
                        log.info(task.getOrderId());
                        task.run();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        runFlyway();
    }

    private void runFlyway() {
        List<LoginProperties.LevelProperties> level = logProperties.getLevel();
        if (level != null){
            level.forEach(m-> changeLogLevel(m.getName(),m.getLevel()));
        }
    }
    private void flushThread() {
        TaskExecutionProperties.Pool pool = taskExecutionProperties.getPool();
        taskExecutor.setMaxPoolSize(pool.getMaxSize());
        taskExecutor.setCorePoolSize(pool.getCoreSize());
        taskExecutor.setQueueCapacity(pool.getQueueCapacity());
        taskExecutor.setKeepAliveSeconds(Math.toIntExact(pool.getKeepAlive().getSeconds()));
        log.info("threadPoolTaskExecutor maxPoolSize{} CorePoolSize {} QueueCapacity {} keepAliveSeconds {} ",taskExecutor.getMaxPoolSize(),taskExecutor.getCorePoolSize(),taskExecutor.getQueueCapacity(),taskExecutor.getKeepAliveSeconds());
    }
}
