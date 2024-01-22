package cn.com.cgh.auth.config;

import cn.com.cgh.auth.constant.MessageConstant;
import cn.com.cgh.auth.exception.VerificationCodeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
