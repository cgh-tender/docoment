package cn.com.cgh.romantic.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author cgh
 */
@Getter
@Setter
@Configuration
@RefreshScope
public class Properties {
    @Value("${spring.cache.cache-names:resource,user,core,gateway}")
    private List<String> cacheNames;
}
