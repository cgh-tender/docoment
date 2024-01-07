package cn.com.cgh.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "classpath:bootstrap.properties")
@Slf4j
public class CoreConfig {
   static {
       log.info("CoreConfig 已启动 bootstrap.properties");
   }
}
