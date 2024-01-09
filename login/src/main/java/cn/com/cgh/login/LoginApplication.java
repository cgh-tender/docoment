package cn.com.cgh.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@Slf4j
public class LoginApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(LoginApplication.class, args);
        InetUtils inetUtils = applicationContext.getBean(InetUtils.class);
        System.out.println(inetUtils.findFirstNonLoopbackAddress().getHostAddress());
    }
}
