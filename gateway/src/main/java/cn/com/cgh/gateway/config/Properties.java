package cn.com.cgh.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
@Getter
@Setter
public class Properties {
    @Value("${allPermissionPath:GET /resource,GET /image/*,POST /doLogin}")
    private String allPermissionPath;
}
