package cn.com.cgh.oasis.config;

import cn.com.cgh.romantic.em.DeclareQueueExchange;
import cn.com.cgh.romantic.em.DeclareQueueName;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Configuration
@AllArgsConstructor
@Slf4j
public class DeadQueueSubscribeProcessor implements ApplicationRunner {
    private final ConnectionFactory targetConnectionFactory;

    static {
        log.info("SubscribeProcessor:已启动");
    }

    /**
     * 消费死信队列信息，并且转发到其他mq
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void subscribeMasterDeadQueue() throws IOException, TimeoutException {
        // 根据id 动态生成队列名称
        // 此处的queueIdList可以从第三方缓存查询得到，并且和sendDelayMsg接口保持同步刷新，此处先用本地缓存代替，id同步刷新机制不是重点，此处暂不讨论
//        for (Integer id : QueueIdListConfig.QUEUE_ID_LIST) {
        Long id = null;
        if (id == null){
            return;
        }
        bindQueryConsumer(id);
//        }
    }

    private void bindQueryConsumer(Object id) throws IOException, TimeoutException {
        String deadQueueName = MessageFormat.format(DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName(), id) + DeclareQueueName.DEAD_QUEUE_NAME_SUFFIX.getQueueName();
        String routingKey = MessageFormat.format(DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName(), id) + DeclareQueueName.ROUTING_QUEUE_NAME_SUFFIX.getQueueName();
        try (Connection connection = targetConnectionFactory.createConnection();
             Channel channel = connection.createChannel(false)) {
            AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(deadQueueName, true, false, false, null);
            if (queueDeclare.getConsumerCount() == 0) {
                channel.exchangeDeclare(DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
            }
            channel.queueBind(deadQueueName, DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), routingKey);
            channel.basicConsume(deadQueueName, false, new DeadQueueConsumer(channel, targetConnectionFactory));
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            String controllerQueueName = MessageFormat.format(DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName(), "controller");
            String loginQueueName = MessageFormat.format(DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName(), "login");

            String deadQueueName = controllerQueueName + DeclareQueueName.DEAD_QUEUE_NAME_SUFFIX.getQueueName();
            String routingKey = controllerQueueName + DeclareQueueName.ROUTING_QUEUE_NAME_SUFFIX.getQueueName();
            try (Connection connection = targetConnectionFactory.createConnection();
                 Channel channel = connection.createChannel(false)) {
                AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(deadQueueName, true, false, false, null);
                if (queueDeclare.getConsumerCount() == 0) {
                    channel.exchangeDeclare(DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
                }
                channel.queueBind(deadQueueName, DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), routingKey);
                channel.basicConsume(deadQueueName, false, new DeadControllerQueueConsumer(channel));

                deadQueueName = loginQueueName + DeclareQueueName.DEAD_QUEUE_NAME_SUFFIX.getQueueName();
                routingKey = loginQueueName + DeclareQueueName.ROUTING_QUEUE_NAME_SUFFIX.getQueueName();
                queueDeclare = channel.queueDeclare(deadQueueName, true, false, false, null);
                if (queueDeclare.getConsumerCount() == 0) {
                    channel.exchangeDeclare(DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
                }
                channel.queueBind(deadQueueName, DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), routingKey);
                channel.basicConsume(deadQueueName, false, new DeadLoginQueueConsumer(channel));


                String delayQueueName = controllerQueueName + DeclareQueueName.DELAY_QUEUE_NAME_SUFFIX.getQueueName();
                routingKey = controllerQueueName + DeclareQueueName.ROUTING_QUEUE_NAME_SUFFIX.getQueueName();
                // 声明延迟队列
                Map<String, Object> params = new HashMap<>();
                //设置延迟队列绑定的死信交换机
                params.put("x-dead-letter-exchange", DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName());
                //设置延迟队列绑定的死信路由键
                params.put("x-dead-letter-routing-key", routingKey);
                //设置延迟队列的 TTL 消息存活时间
                params.put("x-message-ttl", 10 * 1000);
                queueDeclare = channel.queueDeclare(delayQueueName, true, false, false, params);
                if (queueDeclare.getConsumerCount() == 0) {
                    channel.exchangeDeclare(DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
                }
                channel.queueBind(delayQueueName, DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(), routingKey);
                channel.basicConsume(delayQueueName, false, new DelayControllerQueueConsumer(channel));

                delayQueueName = loginQueueName + DeclareQueueName.DELAY_QUEUE_NAME_SUFFIX.getQueueName();
                routingKey = loginQueueName + DeclareQueueName.ROUTING_QUEUE_NAME_SUFFIX.getQueueName();
                params = new HashMap<>();
                //设置延迟队列绑定的死信交换机
                params.put("x-dead-letter-exchange", DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName());
                //设置延迟队列绑定的死信路由键
                params.put("x-dead-letter-routing-key", routingKey);
                //设置延迟队列的 TTL 消息存活时间
                params.put("x-message-ttl", 10 * 1000);
                queueDeclare = channel.queueDeclare(delayQueueName, true, false, false, params);
                if (queueDeclare.getConsumerCount() == 0) {
                    channel.exchangeDeclare(DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
                }
                channel.queueBind(delayQueueName, DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(), routingKey);
                channel.basicConsume(delayQueueName, false, new DelayLoginQueueConsumer(channel));
            }
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
