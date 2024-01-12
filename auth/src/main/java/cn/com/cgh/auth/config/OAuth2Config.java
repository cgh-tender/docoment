package cn.com.cgh.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Slf4j
public class OAuth2Config {

    @Autowired
    private UserDetailsService userService;

    @Value("${auth.whitelist:/login}")
    private String[] URL_WHITELIST;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private OAuth2AuthorizedClientRepository authorizedClientRepository;
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(URL_WHITELIST).permitAll() // 允许访问无需认证的路径
                .anyRequest().authenticated()
        );

        http.cors(Customizer.withDefaults());
//                .csrf(AbstractHttpConfigurer::disable)
        http.formLogin(form -> form.
                loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().write("登录成功");
                    System.out.println(authentication.getAuthorities());
                    System.out.println(authentication.getCredentials());
                    System.out.println(authentication.getDetails());
                    System.out.println(authentication.getPrincipal());
                })
                .failureHandler((request, response, exception) -> {
                    response.setContentType("text/html;charset=utf-8");
                    response.getWriter().write("登录失败");
                    System.out.println(request.getParameter("username"));
                })
        );
        http.exceptionHandling(e->{
            e.accessDeniedHandler((request, response, exception)->{
                log.error("登录失败", exception);
            });
            e.defaultAuthenticationEntryPointFor((request, response, exception)->{
                log.error("登录失败1", exception);
            },null);
        });
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userService)
//                .exceptionHandling(exceptionHandle -> exceptionHandle
//                        .authenticationEntryPoint(loginAuthenticationEntryPoint)
//                        .accessDeniedHandler(authAccessDeniedHandler)
//                )
//                .addFilterAfter(jwtAuthenticationFilter(), ExceptionTranslationFilter.class)
                .httpBasic(Customizer.withDefaults());
        ;
        http.logout(logout -> logout.invalidateHttpSession(true).deleteCookies("Border"));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
    public AuthenticationManager authenticationManagerBean(PasswordEncoder passwordEncoder) throws Exception {
//        new JdbcUserDetailsManager();
        ProviderManager providerManager = new ProviderManager(new DaoAuthenticationProvider() {{
            setUserDetailsService(userService);
            setPasswordEncoder(passwordEncoder);
        }});
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }
}
