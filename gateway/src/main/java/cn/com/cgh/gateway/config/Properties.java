package cn.com.cgh.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class Properties {
    @Value("${allPermissionPath:GET /resource,}")
    private String allPermissionPath;
}
