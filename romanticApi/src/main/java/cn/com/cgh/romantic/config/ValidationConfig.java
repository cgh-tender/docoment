package cn.com.cgh.romantic.config;

import cn.com.cgh.romantic.advice.ValidationExceptionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author cgh
 */
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
