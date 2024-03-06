package cn.com.cgh.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author cgh
 */
@PropertySource(value = "classpath:bootstrap.properties")
@ComponentScan(basePackages = "cn.com.cgh.core.sentinel")
@EnableRetry
@Slf4j
public class CoreConfig {
   static {
       log.info("CoreConfig:已启动 bootstrap.properties");
   }
}
