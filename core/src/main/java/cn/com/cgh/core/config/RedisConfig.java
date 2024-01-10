package cn.com.cgh.core.config;

import cn.com.cgh.core.interfac.RedisMessageAdvice;
import cn.com.cgh.core.util.Constants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
@ConditionalOnProperty(prefix = "spring", name = "data.redis.host")
@EnableCaching
public class RedisConfig {
    static {
        log.info("RedisConfig:已启动");
    }

    @Autowired
    private Properties properties;

    @Bean(value = {"redisTemplate", "redisTemplateOO"})
    public RedisTemplate<Object, Object> redisTemplate(
            LettuceConnectionFactory lettuceConnectionFactory) {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        RedisTemplate<Object, Object> template = getRedisTemplateOO(lettuceConnectionFactory, om);
        template.afterPropertiesSet();
        return template;
    }

    private static RedisTemplate<Object, Object> getRedisTemplateOO(LettuceConnectionFactory lettuceConnectionFactory, ObjectMapper om) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(om, Object.class);
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplateSO(
            LettuceConnectionFactory lettuceConnectionFactory) {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY);
        RedisTemplate<String, Object> template = getStringObjectRedisTemplate(lettuceConnectionFactory, om);
        template.afterPropertiesSet();
        return template;

    }

    private static RedisTemplate<String, Object> getStringObjectRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory, ObjectMapper om) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(om, Object.class);
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        return template;
    }

    @Bean(Constants.REDIS_CACHE_MANAGER_NAME)
    public RedisCacheManager empRedisCacheManager(RedisTemplate<Object, Object> redisTemplateOO, LettuceConnectionFactory lettuceConnectionFactory) {
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
                .fromConnectionFactory(lettuceConnectionFactory)
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
    public RedisMessageListenerContainer redisMessageListenerContainer(LettuceConnectionFactory lettuceConnectionFactory, List<RedisMessageAdvice> messageAdvices) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory);
        if (messageAdvices != null) {
            for (RedisMessageAdvice advice : messageAdvices) {
                container.addMessageListener(advice, advice.getTopic());
            }
        }
        return container;
    }
}