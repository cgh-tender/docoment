package cn.com.cgh.resource.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class Test implements ApplicationRunner {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplateSO;

    @Override
    public void run(ApplicationArguments args) {
        redisTemplateSO.opsForValue().setIfAbsent("test", "test",10,TimeUnit.SECONDS);
    }
}
