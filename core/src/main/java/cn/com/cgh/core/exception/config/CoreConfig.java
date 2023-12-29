package cn.com.cgh.core.exception.config;

import com.feiniaojin.gracefulresponse.EnableGracefulResponse;
import org.springframework.context.annotation.PropertySource;

@EnableGracefulResponse
@PropertySource(value = "classpath:bootstrap.properties")
public class CoreConfig {
   static {
       System.out.println("CoreConfig inited");
   }
}
