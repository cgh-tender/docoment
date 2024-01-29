package cn.com.cgh.oasis.config;

import cn.com.cgh.romantic.em.DeclareQueueExchange;
import cn.com.cgh.romantic.em.DeclareQueueName;
import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author cgh
 */
public class DeadQueueConsumer extends DefaultConsumer {
    private final ConnectionFactory targetConnectionFactory;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public DeadQueueConsumer(Channel channel,ConnectionFactory targetConnectionFactory) {
        super(channel);
        this.targetConnectionFactory = targetConnectionFactory;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        // 从死信队列的名称中截取队列名称，作为后续队列的名称
        String routingKey = envelope.getRoutingKey();
        String targetQueueName = routingKey.substring(0, routingKey.length() - DeclareQueueName.DEAD_QUEUE_NAME_SUFFIX.getQueueName().length());
        try (Connection targetConnection = targetConnectionFactory.createConnection();
             Channel targetChannel = targetConnection.createChannel(false)){
            // 声明交换机和队列
            targetChannel.exchangeDeclare(DeclareQueueExchange.EXCHANGE.getExchangeName(), BuiltinExchangeType.DIRECT);
            targetChannel.queueDeclare(targetQueueName, true, false, false, null);
            targetChannel.queueBind(targetQueueName, DeclareQueueExchange.EXCHANGE.getExchangeName(), targetQueueName);
            // 转发消息
            targetChannel.basicPublish(DeclareQueueExchange.EXCHANGE.getExchangeName(), targetQueueName, properties, body);
        } catch (TimeoutException e) {
            e.printStackTrace();
            // 注意此处获取的源队列的channel
            getChannel().basicNack(envelope.getDeliveryTag(), false, true);
        }
        // 注意此处获取的源队列的channel
        getChannel().basicAck(envelope.getDeliveryTag(), false);
    }

}
