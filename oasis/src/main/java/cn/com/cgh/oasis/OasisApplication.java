package cn.com.cgh.oasis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OasisApplication {
    public static void main(String[] args) {
        SpringApplication.run(OasisApplication.class, args);
    }
}
