package cn.com.utils;

import cn.com.utils.interfaceRun.YmlPropertySourceComponent;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@YmlPropertySourceComponent(value = {"classpath:/resources/AuthFilter.yml","classpath:/resources/AuthFilter2.yml","classpath:auth.properties"})
@ConfigurationProperties(prefix = "data.item")
@Data
public class AuthFilterItemProperties {
    private List<String> item;
    private String name;
    private String AA;
    private String BB;
    private String code;
}
