package cn.com.cgh.core.config;

import cn.com.cgh.core.advice.NotVoidResponseBodyAdvice;
import cn.com.cgh.core.advice.VoidResponseBodyAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ConditionalOnClass(ResponseBodyAdvice.class)
@Slf4j
public class ResponseConfig {
   static {
       log.info("ResponseConfig:已启动");
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
