package cn.com.utils;

import cn.com.utils.interfaceRun.YmlPropertySourceComponent;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:auth.properties")
@ConfigurationProperties(prefix = "test")
@Data
public class testPropertite {
    private String code;
    private String name;
}
