package cn.com.cgh.core.interfac;

import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.Topic;

/**
 * redis 消息监听
 * 需要redis redis.conf 增加配置
 * notify-keyspace-events Ex
 * config set notify-keyspace-events KEA
 * K: PUBLISH __keyspace@0__:mykey del
 * E: PUBLISH __keyevent@0__:del mykey
 * age: __key*__:*
 */
public interface RedisMessageAdvice extends MessageListener {
    public Topic getTopic();
}
