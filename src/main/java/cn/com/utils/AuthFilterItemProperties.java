package cn.com.utils;

import cn.com.utils.interfaceRun.YmlPropertySourceComponent;
import lombok.Data;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@YmlPropertySourceComponent(value = {"classpath:/resources/AuthFilter.yml","classpath:auth.properties"})
@ConfigurationProperties(prefix = "data")
@Data
@Log4j
public class AuthFilterItemProperties {
    private Map<String,String> filterChainDefinitionMap;
    private String hashAlgorithmName;
}
