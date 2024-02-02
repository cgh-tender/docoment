package cn.com.cgh.auth.config;

import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public class UserAuthenticationManager extends AbstractUserDetailsReactiveAuthenticationManager {

    @Override
    protected Mono<UserDetails> retrieveUser(String username) {
        return null;
    }
}
