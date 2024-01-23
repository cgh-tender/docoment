package cn.com.cgh.auth.config;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.pojo.TbCfgUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class OAuth2Config {

    @Autowired
    private RedisTemplate<String, Object> redisTemplateSO;
    @Autowired
    private MyAuthorizationManager myAuthorizationManager;

    @Value("${auth.whitelist:/doLogin,/error,favicon.ico,/hello}")
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
                .successHandler((request, response, authentication) -> {
                    log.info("登录成功");
                    Map<String, String> map = new HashMap<>();
                    TbCfgUser securityUser = (TbCfgUser) authentication.getPrincipal();
                    map.put("token", securityUser.getUsername());
                    response.getWriter().write(ResponseImpl.builder().message("登录成功").data(map).build().SUCCESS().toString());
                })
                .failureHandler((request, response, exception) -> {
                    log.error("登录失败");
                    log.error(exception.getMessage());
                    response.getWriter().write(ResponseImpl.builder().message(exception.getMessage()).build().FULL().toString());
                })
        );
//        http.rememberMe(rememberMe ->
//                        rememberMe
//                        .rememberMeParameter("rememberMe")
//                        .rememberMeCookieName("rememberMe")
//                        .key("myKey")
//                                .tokenRepository(tokenRepository)
//        );
//        http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterAt(new MyFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
//        http.sessionManagement(e -> e.invalidSessionStrategy((request, response) -> {
//                    log.error("会话失效");
//                    response.getWriter().write(ResponseImpl.builder().message("会话失效").build().FULL().toString());
//                }).maximumSessions(1)// 最多一个人登录。
//        );

        http.exceptionHandling(e -> {
            e.accessDeniedHandler((request, response, exception) -> {
                log.error("登录失败", exception);
            });
            e.defaultAuthenticationEntryPointFor((request, response, exception) -> {
                log.error("登录失败1", exception);
            }, null);
        });
        http.logout(logout ->
                logout.invalidateHttpSession(true)
                        .logoutUrl("/logout")
                        .deleteCookies("Border", "rememberMe")
                        .logoutSuccessHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                            log.info("退出成功");
                            response.getWriter().write(ResponseImpl.builder().message("退出成功").build().SUCCESS().toString());
                        }).addLogoutHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                            log.info("开始退出登录");
                        })
        );
        return http.build();
    }

//    @Bean
//    public JdbcUserDetailsManager jdbcUserDetailsManager() {
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
//        jdbcUserDetailsManager.setDataSource(dataSource);
//        jdbcUserDetailsManager.
//        return jdbcUserDetailsManager;
//    }

    //https://www.bilibili.com/video/BV1kj41167rK?p=13&spm_id_from=pageDriver&vd_source=73701b660d71a9d73d9f59e543cc85e7

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
