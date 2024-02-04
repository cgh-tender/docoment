package cn.com.cgh.auth.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author cgh
 */
@Configuration
@Data
public class SecurityConfig {
    @Value("${auth.whitelist:/login,/error,/favicon.ico,/hello}")
    private String[] urlWhitelist;
}
