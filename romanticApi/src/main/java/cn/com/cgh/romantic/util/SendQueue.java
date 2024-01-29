package cn.com.cgh.romantic.util;

import cn.com.cgh.romantic.em.DeclareQueueExchange;
import cn.com.cgh.romantic.em.DeclareQueueName;
import cn.com.cgh.romantic.pojo.MsgPojo;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @author cgh
 */
@AllArgsConstructor
@Slf4j
public class SendQueue implements ApplicationRunner {
    private final ConnectionFactory connectionFactory;
    private final RabbitTemplate rabbitTemplate;

    public void doSendMsg2DelayQueue(MsgPojo<Object> delayMsgVo) throws IOException, TimeoutException {
        // 根据id 动态生成队列名称
        String queueNameTemplate = DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName();
        String queueName = MessageFormat.format(queueNameTemplate, delayMsgVo.getId());
        String delayQueueName = queueName + DeclareQueueName.DELAY_QUEUE_NAME_SUFFIX.getQueueName();
        String deadQueueName = queueName + DeclareQueueName.DEAD_QUEUE_NAME_SUFFIX.getQueueName();
        String routingKey = queueName + DeclareQueueName.ROUTING_QUEUE_NAME_SUFFIX.getQueueName();
        // 注意：下述声明交换机和队列的操作是可以重入的，MQ并不会报错
        try (Connection connection = connectionFactory.createConnection();
             Channel channel = connection.createChannel(false)) {
            // 声明死信交换机
            createAndBindDead(channel, deadQueueName, 10, delayQueueName, routingKey);
            // 发送消息到延迟队列
            channel.basicPublish(DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(), delayQueueName, null,
                    JSONUtil.toJsonStr(delayMsgVo.getMsg()).getBytes(StandardCharsets.UTF_8));
        }
    }

    public void doSendControllerQueue(MsgPojo<Object> delayMsgVo) {
        String queueName = MessageFormat.format(DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName(), "controller");
        String routingKey = queueName + DeclareQueueName.ROUTING_QUEUE_NAME_SUFFIX.getQueueName();
        // 根据id 动态生成队列名称
        rabbitTemplate.convertAndSend(DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(),
                routingKey,
                JSONUtil.toJsonStr(delayMsgVo.getMsg()).getBytes(StandardCharsets.UTF_8)
        );
    }
    public void doSendLoginQueue(MsgPojo<Object> delayMsgVo) {
        String queueNameTemplate = DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName();
        String queueName = MessageFormat.format(queueNameTemplate, "login");
        String routingKey = queueName + DeclareQueueName.ROUTING_QUEUE_NAME_SUFFIX.getQueueName();
        // 根据id 动态生成队列名称
        rabbitTemplate.convertAndSend(DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(),
                routingKey,
                JSONUtil.toJsonStr(delayMsgVo.getMsg()).getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        int second = 10;
        // 注意：下述声明交换机和队列的操作是可以重入的，MQ并不会报错
        try (Connection connection = connectionFactory.createConnection();
             Channel channel = connection.createChannel(false)) {
            String queueName = MessageFormat.format(DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName(), "controller");
            String delayQueueName = queueName + DeclareQueueName.DELAY_QUEUE_NAME_SUFFIX.getQueueName();
            String deadQueueName = queueName + DeclareQueueName.DEAD_QUEUE_NAME_SUFFIX.getQueueName();
            String routingKey = queueName + DeclareQueueName.ROUTING_QUEUE_NAME_SUFFIX.getQueueName();
            createAndBindDead(channel, deadQueueName, second, delayQueueName, routingKey);

            queueName = MessageFormat.format(DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName(), "login");
            delayQueueName = queueName + DeclareQueueName.DELAY_QUEUE_NAME_SUFFIX.getQueueName();
            deadQueueName = queueName + DeclareQueueName.DEAD_QUEUE_NAME_SUFFIX.getQueueName();
            routingKey = queueName + DeclareQueueName.ROUTING_QUEUE_NAME_SUFFIX.getQueueName();
            createAndBindDead(channel, deadQueueName, second, delayQueueName, routingKey);
        }
    }

    private static void createAndBindDead(Channel channel, String deadQueueName, int second, String delayQueueName,String routingKey) throws IOException {
        // 声明死信交换机
        channel.exchangeDeclare(DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
        // 声明死信队列
        channel.queueDeclare(deadQueueName, true, false, false, null);
        // 定时任务 绑定消费者，避免出现多个消费者以及重启后无法消费存量消息的问题
        //  注意：因为需要保证消费顺序，所以此处仅声明一个消费者
        // 死信队列和交换机绑定
        channel.queueBind(deadQueueName, DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), routingKey);
        // 声明延迟队列
        Map<String, Object> params = new HashMap<>();
        //设置延迟队列绑定的死信交换机
        params.put("x-dead-letter-exchange", DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName());
        //设置延迟队列绑定的死信路由键
        params.put("x-dead-letter-routing-key", routingKey);
        //设置延迟队列的 TTL 消息存活时间
        params.put("x-message-ttl", second * 1000);
        channel.queueDeclare(delayQueueName, true, false, false, params);
        channel.exchangeDeclare(DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
        channel.queueBind(delayQueueName, DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(), routingKey);
    }

    @PreDestroy
    public void destory(){
        log.info("关闭连接");
    }
}
