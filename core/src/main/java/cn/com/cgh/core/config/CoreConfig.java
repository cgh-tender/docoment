package cn.com.cgh.core.config;

import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:bootstrap.properties")
public class CoreConfig {
   static {
       System.out.println("CoreConfig已启动 bootstrap.properties");
   }
}
