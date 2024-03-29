package cn.com.cgh.romantic.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * @author cgh
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "spring.cache")
public class Properties {
    private List<Cache> cacheNames;

    @Getter
    @Setter
    public static class Cache{
        private String name;
        private Integer second = 60;
    }
}
