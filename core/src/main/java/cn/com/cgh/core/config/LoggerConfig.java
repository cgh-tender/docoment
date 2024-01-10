package cn.com.cgh.core.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import cn.com.cgh.core.util.CoreDelay;
import cn.hutool.cron.timingwheel.SystemTimer;
import cn.hutool.cron.timingwheel.TimerTask;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

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

    public void changeLogLevel(String loggerName, String level) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = loggerContext.getLogger(loggerName);
        if (logger != null) {
            logger.setLevel(Level.toLevel(level, Level.INFO));
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
                DelayQueue<CoreDelay> queue = new DelayQueue<CoreDelay>();
                CoreDelay coreDelay = new CoreDelay("test", TimeUnit.NANOSECONDS.convert(3, TimeUnit.SECONDS));
                queue.put(coreDelay);
                try {
                    queue.take();
                    runFlyway();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        runFlyway();
        TimerTask timerTask = new TimerTask(() -> {
            log.info("run .............................");
        }, 4000);
        SystemTimer systemTimer = new SystemTimer();
        systemTimer.addTask(timerTask);
        SystemTimer start = systemTimer.start();
    }

    private void runFlyway() {
        List<LoginProperties.LevelProperties> level = logProperties.getLevel();
        if (level != null){
            level.forEach(m-> changeLogLevel(m.getName(),m.getLevel()));
        }
    }
}
