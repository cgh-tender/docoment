package cn.com.cgh.romantic.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "log")
@RefreshScope
public class LoginProperties {
    public List<LevelProperties> level;
    @Data
    public static class LevelProperties {
        private String name;
        private String level;
    }
}
