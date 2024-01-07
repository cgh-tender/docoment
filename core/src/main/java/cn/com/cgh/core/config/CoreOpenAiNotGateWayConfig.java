package cn.com.cgh.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;

@ConditionalOnProperty(name = "gateway" , havingValue = "false", matchIfMissing = true)
@PropertySource(value = "classpath:bootstrap-openai.properties")
@Slf4j
public class CoreOpenAiNotGateWayConfig {
   static {
       log.info("CoreOpenAiNotGateWayConfig 已启动 bootstrap-openai.properties");
   }
}
