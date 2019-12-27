package cn.com.utils;

import cn.com.utils.interfaceRun.YmlPropertySourceComponent;
import cn.com.utils.interfaceRun.YmlPropertySourceProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@YmlPropertySourceComponent(value = {"classpath:/FilterItem/AuthFilter.yml"})
@YmlPropertySourceProperties("data.item")
@Data
public class AuthFilterItemProperties {
    private List<String> item;
    private String name;
}
