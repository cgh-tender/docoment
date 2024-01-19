package cn.com.cgh.core.pubListener.event;

import org.springframework.context.ApplicationEvent;

class MessageSendEvent extends ApplicationEvent {
    public MessageSendEvent(Object source) {
        super(source);
    }
}
