package cn.com.cgh.core.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@ConditionalOnClass(RabbitProperties.class)
public class MqConnectionFactory {

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
        cachingConnectionFactory.setAddresses(addresses);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(vhost);
        return cachingConnectionFactory;
    }


}
