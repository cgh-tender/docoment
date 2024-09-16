package cn.com.cgh.auth.service;

import cn.com.cgh.romantic.server.resource.ILoginController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
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
//        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> .doOnNext(u -> {
//            log.info("loadUserByUsername: {}", u);
//        }).block(), threadPoolTaskExecutor));
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> adminService.loadUserByUsername(username), threadPoolTaskExecutor));
//        return adminService.loadUserByUsername(username);
    }
}
