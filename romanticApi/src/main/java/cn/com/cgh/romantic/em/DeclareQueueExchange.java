package cn.com.cgh.romantic.em;

import lombok.Getter;

/**
 * 队列名称枚举
 * @author cgh
 */
@Getter
public enum DeclareQueueExchange {
    /**
     * 交换机
     */
    EXCHANGE("exchange"),
    /**
     * 死信交换机
     */
    DEAD_EXCHANGE("deadExchange"),
    /**
     * 延迟队列交换机
     */
    DELAY_EXCHANGE("delayExchange"),
    /**
     * 扇型交换机
     */
    FANOUT_EXCHANGE("fanoutExchange"),
    /**
     * 主题交换机
     */
    TOPIC_EXCHANGE("topicExchange"),
    /**
     * 头部换机
     */
    HEADERS_EXCHANGE("headersExchange");

    private final String exchangeName;

    DeclareQueueExchange(String exchangeName) {
        this.exchangeName = exchangeName;
    }

}
