package cn.com.cgh.auth.service;

import cn.com.cgh.auth.constant.MessageConstant;
import cn.com.cgh.romantic.util.ResponseImpl;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.romantic.server.resource.ILoginController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * @author cgh
 */
@Service
@Slf4j
public class UserServiceImpl implements ReactiveUserDetailsService {
    @Autowired
    private ILoginController adminService;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() ->
        {
            ResponseImpl<TbCfgUser> tbCfgUserResponse = adminService.loadUserByUsername(username);
            TbCfgUser tbCfgUser = tbCfgUserResponse.getData();
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
        }, threadPoolTaskExecutor));
    }
}
