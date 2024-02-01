package cn.com.cgh.auth.config;

import cn.com.cgh.auth.filter.SendLogFilter;
import cn.com.cgh.auth.handler.FailureHandler;
import cn.com.cgh.auth.handler.MyLogoutSuccessHandler;
import cn.com.cgh.auth.handler.SuccessHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
public class OAuth2Config {

    @Autowired
    private RedisTemplate<String, Object> redisTemplateSO;
    @Autowired
    private MyAuthorizationManager myAuthorizationManager;

    @Value("${auth.whitelist:/doLogin,/error,/favicon.ico,/hello}")
    private String[] URL_WHITELIST;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeHttpRequest ->
                authorizeHttpRequest
                        .requestMatchers(URL_WHITELIST).permitAll() // 允许访问无需认证的路径
                        .anyRequest().access(myAuthorizationManager)
        );

        http.cors(Customizer.withDefaults());
        http.csrf(csrf->csrf.disable());
        http.formLogin(form -> form.
                loginProcessingUrl("/doLogin")
                .failureForwardUrl("/error")
                .usernameParameter("username")
                .passwordParameter("password")
                .authenticationDetailsSource(new MyWebAuthenticationDetailsSource(redisTemplateSO))
                .successHandler(new SuccessHandler())
                .failureHandler(new FailureHandler())
        );

        http.addFilterBefore(new SendLogFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(e -> {
            e.accessDeniedHandler((request, response, exception) -> {
                log.error("异常1 (0)", exception);
            });
            e.defaultAuthenticationEntryPointFor((request, response, exception) -> {
                log.error("异常2 (0)", exception);
            }, null);
        });

        http.logout(logout ->
                logout.invalidateHttpSession(true)
                        .logoutUrl("/logout")
                        .deleteCookies("Border", "rememberMe")
                        .logoutSuccessHandler(new MyLogoutSuccessHandler())
        );
        return http.build();
    }
}
