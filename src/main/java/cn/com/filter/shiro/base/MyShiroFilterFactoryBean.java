package cn.com.filter.shiro.base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Value;

import java.util.LinkedHashMap;
import java.util.Map;

@Log4j
public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    @Setter
    @Getter
    private Map<String, String> FilterBashChainDefinitionMap;

    @Override
    public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
        setFilterBashChainDefinitionMap(filterChainDefinitionMap);
        super.setFilterChainDefinitionMap(buildFilterChainDefinitionMap());
    }

    private LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        FilterBashChainDefinitionMap.keySet().forEach(k -> {linkedHashMap.put(k,FilterBashChainDefinitionMap.get(k)); });
        linkedHashMap.put("/**", "authc,shiroFilter[admin]");
//        linkedHashMap.put("/**", "authc");
        return linkedHashMap;
    }
}
