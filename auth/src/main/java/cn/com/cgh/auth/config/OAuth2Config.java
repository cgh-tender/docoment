package cn.com.cgh.auth.config;

import cn.com.cgh.auth.filter.SendLogFilter;
import cn.com.cgh.auth.handler.*;
import cn.com.cgh.auth.service.UserServiceImpl;
import cn.com.cgh.romantic.server.resource.ILoginController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author cgh
 */
@Configuration
@Slf4j
@AllArgsConstructor
@Data
public class OAuth2Config {

    private final RedisTemplate<String, Object> redisTemplateSO;
    private final MyAuthorizationManager myAuthorizationManager;
    private final LoginAuthorizationManager loginAuthorizationManager;
    private final TokenLogoutSuccessHandler tokenLogoutSuccessHandler;
    private final TokenLogoutHandler tokenLogoutHandler;
    private final TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final TokenFailureHandler tokenFailureHandler;
    private final TokenSuccessHandler tokenSuccessHandler;
    private final ILoginController initController;
    private final SecurityConfig securityConfig;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl reactiveUserDetailsService;
    private final TokenServerSecurityContextRepository tokenServerSecurityContextRepository;
    private final LoginServerSecurityContextRepository loginServerSecurityContextRepository;


    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(authorizeExchange ->
                authorizeExchange
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers(securityConfig.getUrlWhitelist()).permitAll()
                        .anyExchange().access(myAuthorizationManager) // 其余路径都进行认证
        );
        http.cors(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.formLogin(form -> form
                        .loginPage("/login") // 登录页
                        .authenticationSuccessHandler(tokenSuccessHandler)
                        .securityContextRepository(loginServerSecurityContextRepository)
                        .authenticationFailureHandler(tokenFailureHandler)
        );
//        setServerAuthenticationConverter
//        http.passwordManagement()
        http.authenticationManager(loginAuthorizationManager);
        http.securityContextRepository(tokenServerSecurityContextRepository);
        http.logout(logoutSpec -> logoutSpec.logoutUrl("/logout")
                .logoutHandler(tokenLogoutHandler)
                .logoutSuccessHandler(tokenLogoutSuccessHandler));
        http.addFilterBefore(new SendLogFilter(), SecurityWebFiltersOrder.AUTHORIZATION);
        http.exceptionHandling(e -> e.accessDeniedHandler(tokenAccessDeniedHandler)
                .authenticationEntryPoint(tokenAuthenticationEntryPoint));
        return http.build();
    }

//    @Bean
//    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
//        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
//        authenticationManager.setPasswordEncoder(passwordEncoder);
//        return authenticationManager;
//    }

//    @Bean
//    public ReactiveAuthenticationManager authenticationManager(ReactiveAuthenticationManager reactiveAuthenticationManager) {
//        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
//        authenticationManager.setPasswordEncoder(passwordEncoder);
////        return authenticationManager;
//        LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
//        managers.add(authentication -> {
//            // 其他登陆方式
//            return Mono.empty();
//        });
//        managers.add(authenticationManager);
//        return new DelegatingReactiveAuthenticationManager(managers);
//    }
}
