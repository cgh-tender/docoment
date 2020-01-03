package cn.com.utils;

import cn.com.utils.interfaceRun.YmlPropertySourceComponent;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@YmlPropertySourceComponent(value = {"classpath:/FilterItem/AuthFilter.yml"},name = "data.item")
@Data
public class AuthFilterItemProperties {
    private List<String> item;
    private String name;
}
