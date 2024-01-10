package cn.com.cgh.core.config;

import cn.com.cgh.core.advice.NotVoidResponseBodyAdvice;
import cn.com.cgh.core.advice.ValidationExceptionAdvice;
import cn.com.cgh.core.advice.VoidResponseBodyAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;

@PropertySource(value = "classpath:bootstrap.properties")
@ComponentScan(basePackages = "cn.com.cgh.core.sentinel")
@EnableRetry
@Slf4j
public class CoreConfig {
   static {
       log.info("CoreConfig:已启动 bootstrap.properties");
   }

    @Bean
    @ConditionalOnMissingBean(value = ValidationExceptionAdvice.class)
    public ValidationExceptionAdvice validationExceptionAdvice() {
        return new ValidationExceptionAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(NotVoidResponseBodyAdvice.class)
    public NotVoidResponseBodyAdvice notVoidResponseBodyAdvice() {
        return new NotVoidResponseBodyAdvice();
    }

    @Bean
    @ConditionalOnMissingBean(VoidResponseBodyAdvice.class)
    public VoidResponseBodyAdvice voidResponseBodyAdvice() {
        return new VoidResponseBodyAdvice();
    }
}
