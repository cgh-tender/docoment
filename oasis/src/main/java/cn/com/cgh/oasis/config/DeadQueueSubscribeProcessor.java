package cn.com.cgh.oasis.config;

import cn.com.cgh.core.em.DeclareQueueExchange;
import cn.com.cgh.core.em.DeclareQueueName;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.TimeoutException;

@Configuration
@AllArgsConstructor
@Slf4j
public class DeadQueueSubscribeProcessor {
    private final ConnectionFactory targetConnectionFactory;

    static {
        log.info("DeadQueueSubscribeProcessor");
    }

    /**
     * 消费死信队列信息，并且转发到其他mq
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void subscribeMasterDeadQueue() throws IOException, TimeoutException {
        // 根据id 动态生成队列名称
        // 此处的queueIdList可以从第三方缓存查询得到，并且和sendDelayMsg接口保持同步刷新，此处先用本地缓存代替，id同步刷新机制不是重点，此处暂不讨论
//        for (Integer id : QueueIdListConfig.QUEUE_ID_LIST) {
        Long id = 1L;
        String queueNameTemplate = DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName();
        String deadQueueName = MessageFormat.format(queueNameTemplate, id) + DeclareQueueName.DEAD_QUEUE_NAME_SUFFIX.getQueueName();

        try (Connection connection = targetConnectionFactory.createConnection();
            Channel channel = connection.createChannel(false)) {
            AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(deadQueueName, true, false, false, null);
            if (queueDeclare.getConsumerCount() == 0) {
                channel.exchangeDeclare(DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
            }
            channel.queueBind(deadQueueName, DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), deadQueueName);
            channel.basicConsume(deadQueueName, false, new DeadQueueConsumer(channel, targetConnectionFactory));
        }
    }
//    }

}
