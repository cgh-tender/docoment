package cn.com.cgh.core.pubListener.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class MyOneMessageListener implements ApplicationListener<MessageSendEvent> {
    @Override
    @Async
    public void onApplicationEvent(MessageSendEvent event) {
        try {
            Thread.sleep(3000);
            log.info("{}", event.getSource());
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
    }
}
