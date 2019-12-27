package cn.com.utils.interfaceRun;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class YmlPropertySourcePropertiesAnnotationBeanPostProcessor  extends InstantiationAwareBeanPostProcessorAdapter implements EnvironmentAware, ResourceLoaderAware {
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
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        YmlPropertySourceProperties ymlPropertySource = AnnotationUtils.findAnnotation(bean.getClass(), YmlPropertySourceProperties.class);
        Map<String, List<Resource>> ymlPropertySourceMap = new LinkedHashMap<>();
        if (ymlPropertySource != null) {
            String value = ymlPropertySource.value();
            Resource resource = resourceLoader.getResource(value);
            System.out.println(111);

//            List<Resource> resources = new ArrayList<>();
//            Arrays.stream(value).forEach(location -> {
//                Resource resource = resourceLoader.getResource(location);
//                try {
//                    if (resource.getInputStream() != null) {
//                        resources.add(resource);
//                    }
//                } catch (IOException e) {
//                    System.out.println(MessageFormat.format("file {0} not found.",location));
//                }
//            });
//            ymlPropertySourceMap.put(name, resources);
        }
        System.out.println(beanName);
        return null;
    }

}
