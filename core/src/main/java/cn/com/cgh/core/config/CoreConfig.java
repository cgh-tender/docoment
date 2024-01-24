package cn.com.cgh.core.config;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;

@PropertySource(value = "classpath:bootstrap.properties")
@ComponentScan(basePackages = "cn.com.cgh.core.sentinel")
@EnableRetry
@Slf4j
public class CoreConfig {
    @TableId
    private String id;
   static {
       log.info("CoreConfig:已启动 bootstrap.properties");
   }
}
