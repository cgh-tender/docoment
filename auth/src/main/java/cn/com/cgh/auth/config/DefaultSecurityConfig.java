package cn.com.cgh.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.stereotype.Component;

/**
 *
 * @author cgh
 */
@EnableWebFluxSecurity
@Component
@Slf4j
public class DefaultSecurityConfig {
    static {
        log.info("DefaultSecurityConfig:已启动");
    }
}
