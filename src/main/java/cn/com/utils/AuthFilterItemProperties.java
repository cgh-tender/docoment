package cn.com.utils;

import cn.com.utils.interfaceRun.YmlPropertySourceComponent;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@YmlPropertySourceComponent(value = {"classpath:/resources/AuthFilter.yml","classpath:auth.properties"})
@ConfigurationProperties(prefix = "data")
@Data
public class AuthFilterItemProperties {
    private Map<Object,Object> filterChainDefinitionMap;
    private String hashAlgorithmName;
}
