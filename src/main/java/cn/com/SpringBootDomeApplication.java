package cn.com;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ServletComponentScan("cn.com.**")
@EnableConfigurationProperties
@Log4j
public class SpringBootDomeApplication {

	public static void main(String[] args) {
        log.info(">>>>>>>>>>>>>>> run SpringBootDomeApplication <<<<<<<<<<<<<");
        SpringApplication.run(SpringBootDomeApplication.class, args);
    }



}
