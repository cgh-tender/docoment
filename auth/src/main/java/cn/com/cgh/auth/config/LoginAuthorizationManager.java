package cn.com.cgh.auth.config;

import cn.com.cgh.auth.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 实现权限控制。
 *
 * @author cgh
 */
@Component
@Slf4j
public class LoginAuthorizationManager extends AbstractUserDetailsReactiveAuthenticationManager {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public LoginAuthorizationManager(PasswordEncoder passwordEncoder) {
        setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected Mono<UserDetails> retrieveUser(String username) {
        return userService.findByUsername(username);
    }
}
