package cn.com.cgh.core.exception.config;

import com.feiniaojin.gracefulresponse.AbstractExceptionAliasRegisterConfig;
import com.feiniaojin.gracefulresponse.EnableGracefulResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.PropertySource;

@ConditionalOnClass(value = {AbstractExceptionAliasRegisterConfig.class})
@EnableGracefulResponse
@PropertySource(value = "classpath:bootstrap.properties")
public class CoreConfig {
   static {
       System.out.println("CoreConfig inited");
   }
}
