package cn.com.cgh.romantic.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "permission")
@RefreshScope
public class PermissionConfig {
    private Set<Object> adminUser;
}
