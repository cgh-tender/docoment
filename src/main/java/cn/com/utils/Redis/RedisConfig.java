package cn.com.utils.Redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisConfig {
    private int database;
    private String host;
    private String port;
    private String password;
    private String timeout;
    private String nodes;
    private String maxActive;
}
