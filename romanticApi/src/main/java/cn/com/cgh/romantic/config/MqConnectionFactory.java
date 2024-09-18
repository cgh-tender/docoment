package cn.com.cgh.romantic.config;

import cn.com.cgh.romantic.util.RabbitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author cgh
 */
@ConditionalOnClass(RabbitProperties.class)
@Slf4j
public class MqConnectionFactory {
    static {
        log.info("MqConnectionFactory:已启动");
    }

    @Bean
    @Primary
    public ConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {
        return doCreateConnectionFactory(rabbitProperties.getAddresses(), rabbitProperties.getUsername(), rabbitProperties.getPassword(), rabbitProperties.getVirtualHost());
    }

    private ConnectionFactory doCreateConnectionFactory(String addresses,
                                                        String username,
                                                        String password,
                                                        String vhost) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//        cachingConnectionFactory.setUri("amqp://"+username+":"+password+"@"+addresses+"/"+vhost);
        cachingConnectionFactory.setAddresses(addresses);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(vhost);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }


    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("消息：{}发送成功", correlationData.getId());
            } else {
                log.error("消息：{}发送失败，失败原因为：{}", correlationData.getId(), cause);
            }
        });
        template.setMandatory(true);
        template.setReturnsCallback(returned -> {
            log.error("消息：{}路由失败, 失败原因为：{}", returned.getMessage().toString(), returned.getReplyText());
        });
        return template;
    }
    
     @Bean
    public RabbitUtil rabbitUtil(){
        return new RabbitUtil();
    }
}
