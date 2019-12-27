package cn.com.utils.interfaceRun;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class YmlPropertySourceAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements EnvironmentAware, ResourceLoaderAware {

    private Environment environment;
    private ResourceLoader resourceLoader;

    @Override
    public void setEnvironment(Environment environment) {
        Assert.isInstanceOf(ConfigurableEnvironment.class, environment, "environment must be instance of ConfigurableEnvironment.");
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        YmlPropertySourceComponent ymlPropertySource = AnnotationUtils.findAnnotation(bean.getClass(), YmlPropertySourceComponent.class);
        Map<String, List<Resource>> ymlPropertySourceMap = new LinkedHashMap<>();
        if (ymlPropertySource != null) {
            String[] value = ymlPropertySource.value();
            String name = ymlPropertySource.name();
            List<Resource> resources = new ArrayList<>();
            Arrays.stream(value).forEach(location -> {
                Resource resource = resourceLoader.getResource(location);
                try {
                    if (resource.getInputStream() != null) {
                        resources.add(resource);
                    }
                } catch (IOException e) {
                    System.out.println(MessageFormat.format("file {0} not found.",location));
                }
            });
            ymlPropertySourceMap.put(name, resources);
        }
        if (!ymlPropertySourceMap.isEmpty()) {
            System.out.println("beanName: "+beanName+" 执行..postProcessAfterInstantiation 加载配置文件");
            ymlPropertySourceMap.forEach((name, resources) -> {
                YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
                yamlPropertiesFactoryBean.setResources(resources.toArray(new Resource[resources.size()]));
                PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(resources.get(0).toString(), yamlPropertiesFactoryBean.getObject());
                ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
                MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
                if (propertySources.get(resources.get(0).toString()) != null){
                    propertySources.remove(resources.get(0).toString());
                }
                propertySources.addLast(propertiesPropertySource);
            });
        }
        return true;
    }
}
