package cn.com.cgh.auth.config;

import cn.com.cgh.auth.constant.MessageConstant;
import cn.com.cgh.auth.exception.VerificationCodeException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static cn.com.cgh.auth.constant.MessageConstant.USERNAME_PASSWORD_ERROR;

@Component
public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider {
    public MyDaoAuthenticationProvider(UserDetailsService userDeleteService, PasswordEncoder passwordEncoder) {
        super.setUserDetailsService(userDeleteService);
        super.setPasswordEncoder(passwordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        if (details.getVerificationCodeLose()){
            throw new VerificationCodeException(MessageConstant.VERIFICATION_DENIED_LOSE);
        }
        if (details.getVerificationCode()){
            throw new VerificationCodeException(MessageConstant.VERIFICATION_DENIED);
        }
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
            throw new BadCredentialsException(USERNAME_PASSWORD_ERROR);
        }
    }
}
