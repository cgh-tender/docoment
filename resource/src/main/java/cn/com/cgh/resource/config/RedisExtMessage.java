package cn.com.cgh.resource.config;

import cn.com.cgh.core.interfac.RedisMessageAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisExtMessage implements RedisMessageAdvice {
    @Override
    public Topic getTopic() {
        return new PatternTopic("__keyevent@0__:expired");
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("redis删除key:{}", new String(message.getBody()));
    }
}
