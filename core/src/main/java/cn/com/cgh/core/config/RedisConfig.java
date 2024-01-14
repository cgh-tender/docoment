package cn.com.cgh.core.config;

import cn.com.cgh.core.interfac.RedisMessageAdvice;
import cn.com.cgh.core.util.Constants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import redis.clients.jedis.ConnectionPoolConfig;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.List;

/**
 * {@code @Cacheable}
 * {@code @CachePut}
 * {@code @CacheEvict}
 * {@code @Caching}
 * {@code @CacheConfig}
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "spring", name = "data.redis.host")
@EnableCaching
@AutoConfigureBefore(value = {RedisAutoConfiguration.class})
public class RedisConfig {
    static {
        log.info("RedisConfig:已启动");
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties){

        //redis配置
        RedisConfiguration redisConfiguration = new
                RedisStandaloneConfiguration(redisProperties.getHost(),redisProperties.getPort());
        ((RedisStandaloneConfiguration) redisConfiguration).setDatabase(redisProperties.getDatabase());
        ((RedisStandaloneConfiguration) redisConfiguration).setPassword(redisProperties.getPassword());

        //连接池配置
        JedisPoolConfig genericObjectPoolConfig = new JedisPoolConfig();
        genericObjectPoolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        genericObjectPoolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        genericObjectPoolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        genericObjectPoolConfig.setMaxWait(Duration.ofMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis()));

        //redis客户端配置
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
                builder =  LettucePoolingClientConfiguration.builder().
                commandTimeout(Duration.ofMillis(redisProperties.getTimeout().toMillis()));

//        builder.shutdownTimeout(Duration.ofMillis(redisProperties.getS));
        builder.poolConfig(genericObjectPoolConfig);
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();

        //根据配置和客户端配置创建连接
        LettuceConnectionFactory lettuceConnectionFactory = new
                LettuceConnectionFactory(redisConfiguration,lettuceClientConfiguration);
        lettuceConnectionFactory .afterPropertiesSet();

        return lettuceConnectionFactory;
    }

    @ConditionalOnMissingBean(
            name = {"redisTemplate"}
    )
    @Bean(value = {"redisTemplate"})
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        RedisTemplate<Object, Object> template = getRedisTemplateOO(redisConnectionFactory, om);
        template.afterPropertiesSet();
        return template;
    }

    @Autowired
    private Properties properties;

    private static RedisTemplate<Object, Object> getRedisTemplateOO(RedisConnectionFactory redisConnectionFactory, ObjectMapper om) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(om, Object.class);
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplateSO(
            RedisConnectionFactory redisConnectionFactory) {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        RedisTemplate<String, Object> template = getStringObjectRedisTemplate(redisConnectionFactory, om);
        template.afterPropertiesSet();
        return template;

    }

    private static RedisTemplate<String, Object> getStringObjectRedisTemplate(RedisConnectionFactory redisConnectionFactory, ObjectMapper om) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(om, Object.class);
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean(Constants.REDIS_CACHE_MANAGER_NAME)
    public RedisCacheManager empRedisCacheManager(RedisTemplate<Object, Object> redisTemplateOO, RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
                .defaultCacheConfig()
                // 设置key为String
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplateOO.getStringSerializer()))
                // 设置value 为自动转Json的Object
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplateOO.getValueSerializer()))
                // 不缓存null
                .disableCachingNullValues()
                // 缓存数据保存30s
                .entryTtl(Duration.ofSeconds(60));
        // 构造一个redis缓存管理器
        RedisCacheManager.RedisCacheManagerBuilder redisCacheManagerBuilder = RedisCacheManager
                .RedisCacheManagerBuilder
                // Redis 连接工厂
                .fromConnectionFactory(redisConnectionFactory)
                // 设置默认缓存配置
                .cacheDefaults(redisCacheConfiguration);
        if (properties.getCacheNames() != null) {
            properties.getCacheNames().forEach(key -> redisCacheManagerBuilder.withCacheConfiguration(key, redisCacheConfiguration.entryTtl(Duration.ofSeconds(60))));
        }
        // 设置自定义缓存配置，缓存名为cache_user，它的过期时间为60s
        redisCacheManagerBuilder.withCacheConfiguration("demo", redisCacheConfiguration.entryTtl(Duration.ofSeconds(60)))
                .transactionAware();
        return redisCacheManagerBuilder.build();
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, List<RedisMessageAdvice> messageAdvices) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        if (messageAdvices != null) {
            for (RedisMessageAdvice advice : messageAdvices) {
                container.addMessageListener(advice, advice.getTopic());
            }
        }
        return container;
    }
}