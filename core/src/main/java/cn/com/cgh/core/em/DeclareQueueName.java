package cn.com.cgh.core.em;

/**
 * 队列名称枚举
 */
public enum DeclareQueueName {
    /**
     * 延迟队列
     */
    DELAY_QUEUE_NAME_SUFFIX("_delay"),
    /**
     * 死信队列
     */
    DEAD_QUEUE_NAME_SUFFIX("_dead"),
    /**
     * 普通队列
     */
    QUEUE_NAME_SUFFIX("_queue"),
    /**
     * 队列名称模板
     */
    QUEUE_NAME_TEMPLATE("wd.simple.queue.{0}");

    private final String queueName;

    DeclareQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

}
