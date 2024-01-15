package cn.com.cgh.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Slf4j
public class LoginApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LoginApplication.class, args);
        RedisTemplate bean = (RedisTemplate) context.getBean("redisTemplateSO");
        bean.opsForValue().setIfAbsent("test","test",3, TimeUnit.SECONDS);

    }
}
