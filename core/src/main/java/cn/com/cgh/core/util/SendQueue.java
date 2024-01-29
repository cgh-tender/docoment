package cn.com.cgh.core.util;

import cn.com.cgh.core.em.DeclareQueueExchange;
import cn.com.cgh.core.em.DeclareQueueName;
import cn.com.cgh.core.pojo.MsgPojo;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@AllArgsConstructor
public class SendQueue {
    private final ConnectionFactory connectionFactory;

    public void doSendMsg2DelayQueue(MsgPojo delayMsgVo) throws IOException, TimeoutException {
        // 根据id 动态生成队列名称
        String queueNameTemplate = DeclareQueueName.QUEUE_NAME_TEMPLATE.getQueueName();
        String queueName = MessageFormat.format(queueNameTemplate, delayMsgVo.getId());
        String delayQueueName = queueName + DeclareQueueName.DELAY_QUEUE_NAME_SUFFIX.getQueueName();
        String deadQueueName = queueName + DeclareQueueName.DEAD_QUEUE_NAME_SUFFIX.getQueueName();
        // 注意：下述声明交换机和队列的操作是可以重入的，MQ并不会报错
        try (Connection connection = connectionFactory.createConnection();
             Channel channel = connection.createChannel(false)) {
            // 声明死信交换机
            channel.exchangeDeclare(DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
            // 声明死信队列
            channel.queueDeclare(deadQueueName,true, false, false, null);
            // 定时任务 绑定消费者，避免出现多个消费者以及重启后无法消费存量消息的问题
            //  注意：因为需要保证消费顺序，所以此处仅声明一个消费者
            // 死信队列和交换机绑定
            channel.queueBind(deadQueueName, DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName(), deadQueueName);

            // 声明延迟队列
            Map<String, Object> args = new HashMap<>();
            //设置延迟队列绑定的死信交换机
            args.put("x-dead-letter-exchange", DeclareQueueExchange.DEAD_EXCHANGE.getExchangeName());
            //设置延迟队列绑定的死信路由键
            args.put("x-dead-letter-routing-key", deadQueueName);
            //设置延迟队列的 TTL 消息存活时间
            args.put("x-message-ttl", 10 * 1000);
            channel.queueDeclare(delayQueueName, true, false, false, args);
            channel.exchangeDeclare(DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
            channel.queueBind(delayQueueName, DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(), delayQueueName);

            // 发送消息到延迟队列
            channel.basicPublish(DeclareQueueExchange.DELAY_EXCHANGE.getExchangeName(), delayQueueName, null,
                    JSONUtil.toJsonStr(delayMsgVo.getMsg()).getBytes(StandardCharsets.UTF_8));
        }
    }

}
