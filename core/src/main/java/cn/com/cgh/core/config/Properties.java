package cn.com.cgh.core.config;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
@RefreshScope
public class Properties {
    @Value("${spring.cache.cache-names:login,user,core,gateway}")
    private List<String> cacheNames;

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
}
