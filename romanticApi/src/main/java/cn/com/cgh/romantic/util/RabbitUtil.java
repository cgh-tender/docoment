package cn.com.cgh.romantic.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author cgh
 */
@Slf4j
public class RabbitUtil {

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 创建Exchange
     *
     * @param exchangeName
     */
    public void addExchange(String exchangeType, String exchangeName) {
        Exchange exchange = createExchange(exchangeType, exchangeName);
        rabbitAdmin.declareExchange(exchange);
    }

    /**
     * 删除一个Exchange
     *
     * @param exchangeName
     */
    public boolean deleteExchange(String exchangeName) {
        return rabbitAdmin.deleteExchange(exchangeName);
    }

    /**
     * 创建一个指定的Queue
     *
     * @param queueName
     * @return queueName
     */
    public void addQueue(String queueName) {
        Queue queue = createQueue(queueName);
        rabbitAdmin.declareQueue(queue);
    }

    /**
     * 删除一个queue
     *
     * @param queueName
     * @return queueName
     */
    public boolean deleteQueue(String queueName) {
        return rabbitAdmin.deleteQueue(queueName);
    }

    /**
     * 按照筛选条件，删除队列
     *
     * @param queueName
     * @param unused    是否被使用
     * @param empty     内容是否为空
     */
    public void deleteQueue(String queueName, boolean unused, boolean empty) {
        rabbitAdmin.deleteQueue(queueName, unused, empty);
    }

    /**
     * 清空某个队列中的消息，注意，清空的消息并没有被消费
     *
     * @param queueName
     * @return queueName
     */
    public void purgeQueue(String queueName) {
        rabbitAdmin.purgeQueue(queueName, false);
    }

    /**
     * 判断指定的队列是否存在
     *
     * @param queueName
     * @return
     */
    public boolean existQueue(String queueName) {
        return rabbitAdmin.getQueueProperties(queueName) != null;
    }

    /**
     * 绑定一个队列到一个匹配型交换器使用一个routingKey
     *
     * @param exchangeType
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param isWhereAll
     * @param headers      EADERS模式类型设置，其他模式类型传空
     */
    public void addBinding(String exchangeType, String exchangeName, String queueName, String routingKey, boolean isWhereAll, Map<String, Object> headers) {
        Binding binding = bindingBuilder(exchangeType, exchangeName, queueName, routingKey, isWhereAll, headers);
        rabbitAdmin.declareBinding(binding);
    }

    /**
     * 声明绑定
     *
     * @param binding
     */
    public void addBinding(Binding binding) {
        rabbitAdmin.declareBinding(binding);
    }

    /**
     * 解除交换器与队列的绑定
     *
     * @param exchangeType
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param isWhereAll
     * @param headers
     */
    public void removeBinding(String exchangeType, String exchangeName, String queueName, String routingKey, boolean isWhereAll, Map<String, Object> headers) {
        Binding binding = bindingBuilder(exchangeType, exchangeName, queueName, routingKey, isWhereAll, headers);
        removeBinding(binding);
    }

    /**
     * 解除交换器与队列的绑定
     *
     * @param binding
     */
    public void removeBinding(Binding binding) {
        rabbitAdmin.removeBinding(binding);
    }

    /**
     * 创建一个交换器、队列，并绑定队列
     *
     * @param exchangeType
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param isWhereAll
     * @param headers
     */
    public void andExchangeBindingQueue(String exchangeType, String exchangeName, String queueName, String routingKey, boolean isWhereAll, Map<String, Object> headers) {
        //声明交换器
        addExchange(exchangeType, exchangeName);
        //声明队列
        addQueue(queueName);
        //声明绑定关系
        addBinding(exchangeType, exchangeName, queueName, routingKey, isWhereAll, headers);
    }

    /**
     * 发送消息
     *
     * @param exchange
     * @param routingKey
     * @param object
     */
    public void convertAndSend(String exchange, String routingKey, final Object object) {
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }

    /**
     * 转换Message对象
     *
     * @param messageType
     * @param msg
     * @return
     */
    public Message getMessage(String messageType, Object msg) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(messageType);
        Message message = new Message(msg.toString().getBytes(), messageProperties);
        return message;
    }

    /**
     * 声明交换机
     *
     * @param exchangeType
     * @param exchangeName
     * @return
     */
    private Exchange createExchange(String exchangeType, String exchangeName) {
        if (ExchangeTypes.DIRECT.equals(exchangeType)) {
            return new DirectExchange(exchangeName);
        }
        if (ExchangeTypes.TOPIC.equals(exchangeType)) {
            return new TopicExchange(exchangeName);
        }
        if (ExchangeTypes.HEADERS.equals(exchangeType)) {
            return new HeadersExchange(exchangeName);
        }
        if (ExchangeTypes.FANOUT.equals(exchangeType)) {
            return new FanoutExchange(exchangeName);
        }
        return null;
    }

    /**
     * 声明绑定关系
     *
     * @param exchangeType
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param isWhereAll
     * @param headers
     * @return
     */
    private Binding bindingBuilder(String exchangeType, String exchangeName, String queueName, String routingKey, boolean isWhereAll, Map<String, Object> headers) {
        if (ExchangeTypes.DIRECT.equals(exchangeType)) {
            return BindingBuilder.bind(new Queue(queueName)).to(new DirectExchange(exchangeName)).with(routingKey);
        }
        if (ExchangeTypes.TOPIC.equals(exchangeType)) {
            return BindingBuilder.bind(new Queue(queueName)).to(new TopicExchange(exchangeName)).with(routingKey);
        }
        if (ExchangeTypes.HEADERS.equals(exchangeType)) {
            if (isWhereAll) {
                return BindingBuilder.bind(new Queue(queueName)).to(new HeadersExchange(exchangeName)).whereAll(headers).match();
            } else {
                return BindingBuilder.bind(new Queue(queueName)).to(new HeadersExchange(exchangeName)).whereAny(headers).match();
            }
        }
        if (ExchangeTypes.FANOUT.equals(exchangeType)) {
            return BindingBuilder.bind(new Queue(queueName)).to(new FanoutExchange(exchangeName));
        }
        return null;
    }

    /**
     * 声明队列
     *
     * @param queueName
     * @return
     */
    private Queue createQueue(String queueName) {
        return new Queue(queueName);
    }
}
