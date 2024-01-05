package cn.com.cgh.core.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import static java.util.HashSet.newHashSet;

@Profile(value = {"dev", "test"})
@Slf4j
@EnableSwagger2
public class SwaggerConfig  {
    static {
        log.info("SwaggerConfig1已启动");
    }

    @Bean
    public Docket api(Environment environment) {
        // 设置要显示的Swagger环境
        Profiles profiles = Profiles.of("dev", "test");
        boolean flag = environment.acceptsProfiles(profiles);
        Set<String> protocols = newHashSet(2);
        protocols.add("https");
        protocols.add("http");
        // 获取项目环境：是生产环境还是发布环境
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(// 指定扫描的controller包
                        RequestHandlerSelectors.basePackage("cn.com.cgh.login")
                ).paths(// 通过paths()方法配置扫描接口,PathSelectors配置如何扫描接口
                        PathSelectors.ant("/**")
                ).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("APIs")
                .description("cgh测试")
                .contact(new Contact("陈", "https://github.com/cgh-tender/docoment", "late_tender@163.com"))
                .version("1.0")
                .build();
    }

    @Bean
    public RequestMappingHandlerMapping customRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
        mapping.setUseSuffixPatternMatch(false);
        return mapping;
    }
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        try {
//            Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
//            List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
//            if (registrations != null) {
//                for (InterceptorRegistration interceptorRegistration : registrations) {
//                    interceptorRegistration
//                            .excludePathPatterns("/swagger**/**")
//                            .excludePathPatterns("/webjars/**")
//                            .excludePathPatterns("/v3/**")
//                            .excludePathPatterns("/doc.html");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
