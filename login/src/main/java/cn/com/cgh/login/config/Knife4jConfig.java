package cn.com.cgh.login.config;

import cn.hutool.core.util.RandomUtil;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@Profile(value = {"dev", "test"})
@Slf4j
public class Knife4jConfig {
    static {
        log.info("SwaggerConfig1 已启动");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .security(securitySchemes())
                .externalDocs(new ExternalDocumentation()
                .description("SpringDoc Wiki Documentation")
                .url("https://springdoc.org/v2"))
                ;

    }

    private Info apiInfo() {
        return new Info()
                .title("AUTOTextArea API")
                .description("cgh测试")
                .contact(new Contact()
                        .name("陈")
                        .email("late_tender@163.com")
                        .url("https://github.com/cgh-tender/docoment"))
                .version("1.0")
                .license(new License()
                        .name("Apache 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0.txt")
                )
                ;
    }
    private List<SecurityRequirement> securitySchemes() {
        List<SecurityRequirement> apiKeyList= new ArrayList<>();
        //注意，这里应对应登录token鉴权对应的k-v
        apiKeyList.add(new SecurityRequirement().addList("Authorization", "Authorization"));
        return apiKeyList;
    }

    @Bean
    public GlobalOpenApiCustomizer orderGlobalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getTags()!=null){
                openApi.getTags().forEach(tag -> {
                    Map<String,Object> map=new HashMap<>();
                    map.put("x-order", RandomUtil.randomInt(0,100));
                    tag.setExtensions(map);
                });
            }
            if(openApi.getPaths()!=null){
                openApi.addExtension("x-test123","333");
                openApi.getPaths().addExtension("x-abb",RandomUtil.randomInt(1,100));
            }

        };
    }


}
