package cn.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ServletComponentScan("cn.com")
@EnableConfigurationProperties
public class SpringBootDomeApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootDomeApplication.class, args);
    }



}
