package cn.com.cgh.romantic.pubListener.event;

import cn.com.cgh.romantic.pubListener.listener.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@Slf4j
public class EventTest implements ApplicationRunner {
    @Autowired
    private EventPublisher eventPublisher;
    @Override
    public void run(ApplicationArguments args) {
        eventPublisher.publishEvent(new MessageSendEvent("test event"));
        log.info("test event ok");
    }
}
