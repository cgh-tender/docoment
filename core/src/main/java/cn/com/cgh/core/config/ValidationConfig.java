package cn.com.cgh.core.config;

import cn.com.cgh.core.advice.ValidationExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@Slf4j
public class ValidationConfig {
   static {
       log.info("ValidationConfig:已启动");
   }

    @Bean
    @ConditionalOnMissingBean(value = ValidationExceptionAdvice.class)
    public ValidationExceptionAdvice validationExceptionAdvice() {
        return new ValidationExceptionAdvice();
    }
}
