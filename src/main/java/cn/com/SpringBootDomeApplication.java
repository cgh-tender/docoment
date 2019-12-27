package cn.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ServletComponentScan("cn.com")
public class SpringBootDomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDomeApplication.class, args);
	}



}
