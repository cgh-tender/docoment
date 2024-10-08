package cn.com.cgh.auth.config;

import cn.com.cgh.auth.filter.ManageAuthenticationFilter;
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
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Flux;

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
    private final LoginFailureHandler loginFailureHandler;
    private final LoginSuccessHandler loginSuccessHandler;
    private final ILoginController initController;
    private final SecurityConfig securityConfig;
    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl reactiveUserDetailsService;
    private final TokenServerSecurityContextRepository tokenServerSecurityContextRepository;
    private final LoginServerSecurityContextRepository loginServerSecurityContextRepository;
    private final SendLogFilter sendLogFilter;
    private final ManageAuthenticationFilter manageAuthenticationFilter;
    private final MyServerAuthenticationConverter myServerAuthenticationConverter;


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
                .authenticationSuccessHandler(loginSuccessHandler)
                .securityContextRepository(loginServerSecurityContextRepository)
                .authenticationFailureHandler(loginFailureHandler)
                .authenticationManager(loginAuthorizationManager)
        );
        http.securityContextRepository(tokenServerSecurityContextRepository);
        http.logout(logoutSpec -> logoutSpec.logoutUrl("/logout")
                .logoutHandler(tokenLogoutHandler)
                .logoutSuccessHandler(tokenLogoutSuccessHandler));
        http.addFilterBefore(sendLogFilter, SecurityWebFiltersOrder.FORM_LOGIN);
        http.addFilterBefore(manageAuthenticationFilter, SecurityWebFiltersOrder.FORM_LOGIN);
        http.exceptionHandling(e -> e.accessDeniedHandler(tokenAccessDeniedHandler)
                        // 处理未授权
                .authenticationEntryPoint(tokenAuthenticationEntryPoint)
                //处理未认证
                // 自定义处理JWT请求头过期或签名错误的结果
        );
        SecurityWebFilterChain build = http.build();
        Flux<WebFilter> webFilterFlux = build.getWebFilters().doOnNext(filter -> {
            if (filter instanceof AuthenticationWebFilter) {
                AuthenticationWebFilter authenticationWebFilter = (AuthenticationWebFilter) filter;
                authenticationWebFilter.setServerAuthenticationConverter(myServerAuthenticationConverter);
            }
        });
        webFilterFlux.subscribe();
        return build;
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
