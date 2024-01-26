package cn.com.cgh.core.em;

/**
 * 队列名称枚举
 */
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
    DELAY_EXCHANGE("delayExchange");

    private final String exchangeName;

    DeclareQueueExchange(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchangeName() {
        return exchangeName;
    }

}
