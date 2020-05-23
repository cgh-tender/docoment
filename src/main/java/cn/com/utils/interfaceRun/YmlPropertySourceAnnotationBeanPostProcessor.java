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
        List<Resource> resources = new ArrayList<>();
        if (ymlPropertySource != null) {
            String[] value = ymlPropertySource.value();
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
        }
        if (!resources.isEmpty()){
            resources.forEach((resource) -> {
                YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
                yamlPropertiesFactoryBean.setResources(resource);
                PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(resource.toString(), yamlPropertiesFactoryBean.getObject());
                ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
                MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
                if (propertySources.get(resource.toString()) == null){
                    propertySources.addLast(propertiesPropertySource);
                }
            });
        }
        return true;
    }
}
