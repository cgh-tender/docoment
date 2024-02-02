package cn.com.cgh.auth.service;

import cn.com.cgh.auth.constant.MessageConstant;
import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.romantic.server.resource.ILoginController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
@Service
public class UserServiceImpl implements ReactiveUserDetailsService {
    @Autowired
    private ILoginController adminService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        Mono<ResponseImpl<TbCfgUser>> responseMono = adminService.loadUserByUsername(username);
        return responseMono.map((ResponseImpl<TbCfgUser> resource) -> {
            TbCfgUser tbCfgUser = resource.getData();
            if (tbCfgUser == null) {
                throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
            }
            tbCfgUser.setClientId("");
            if (!tbCfgUser.isEnabled()) {
                throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
            } else if (!tbCfgUser.isAccountNonLocked()) {
                throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
            } else if (!tbCfgUser.isAccountNonExpired()) {
                throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
            } else if (!tbCfgUser.isCredentialsNonExpired()) {
                throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
            }
            return tbCfgUser;
        });
    }
}
