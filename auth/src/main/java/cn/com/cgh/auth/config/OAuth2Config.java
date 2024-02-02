package cn.com.cgh.auth.config;

import cn.com.cgh.auth.filter.SendLogFilter;
import cn.com.cgh.auth.handler.FailureHandler;
import cn.com.cgh.auth.handler.MyLogoutSuccessHandler;
import cn.com.cgh.auth.handler.SuccessHandler;
import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.romantic.server.resource.ILoginController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
@Configuration
@Slf4j
public class OAuth2Config {

    @Autowired
    private RedisTemplate<String, Object> redisTemplateSO;
    @Autowired
    private MyAuthorizationManager myAuthorizationManager;
    @Autowired
    private ILoginController initController;

    @Value("${auth.whitelist:/doLogin,/error,/favicon.ico,/hello}")
    private String[] URL_WHITELIST;

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(authorizeExchange ->
                authorizeExchange
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers(URL_WHITELIST).permitAll()
                        .anyExchange().access(myAuthorizationManager) // 其余路径都进行认证
        );
        http.securityContextRepository(new ServerSecurityContextRepository() {
            @Override
            public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
                return Mono.empty();
            }
            @Override
            public Mono<SecurityContext> load(ServerWebExchange exchange) {
                log.info("加载token:JwtSecurityContextRepository");
                String path = exchange.getRequest().getPath().toString();
                // 过滤路径
                if ("/doLogin".equals(path)) {
                    return Mono.empty();
                }
                String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (StringUtils.isNotBlank(token)) {
                    // token能正常解析，表示token有效并对应数据库已知用户
                    String subject = "admin";
                    Authentication newAuthentication = new UsernamePasswordAuthenticationToken(subject, subject);
                    return new ReactiveAuthenticationManager() {
                        @Override
                        public Mono<Authentication> authenticate(Authentication authentication) {
                            // 如果对token有足够的安全认可，可以采用无状态凭证策略，将username和authorities放置在token串中解析获取，此处就可以不用查询数据库验证
                            Mono<ResponseImpl<TbCfgUser>> responseMono = initController.loadUserByUsername(String.valueOf(authentication.getPrincipal()));
                            ResponseImpl<TbCfgUser> userResponse = responseMono.block();
                            TbCfgUser user = userResponse.getData();
                            if (user == null) {
                                throw new DisabledException("账户不可用");
                            }
                            Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                            return Mono.just(auth);
                        }
                    }.authenticate(newAuthentication).map(SecurityContextImpl::new);
                } else {
                    return Mono.empty();
                }
            }
        });
//        http.authenticationManager(myAuthorizationManager -> {
//            myAuthorizationManager.setAuthenticated(true);
//            log.info("认证中心");
//            return null;
//        });
        http.cors(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.formLogin(form -> form
                .loginPage("/doLogin") // 登录页
                .authenticationSuccessHandler(new SuccessHandler())
                .authenticationFailureHandler(new FailureHandler())
        );
//        http.passwordManagement(passwordManager -> {
//            log.info("密码管理");
//            passwordManager.changePasswordPage("12345678");
//        });
        http.logout(logoutSpec -> {
            logoutSpec.logoutUrl("/logout")
                    .logoutSuccessHandler(new MyLogoutSuccessHandler())
            ;
        });
        http.addFilterBefore(new SendLogFilter(), SecurityWebFiltersOrder.AUTHORIZATION);
        http.exceptionHandling(e->{
           e.accessDeniedHandler((exchange, denied) -> {
               log.error("异常1 (0)", denied);
               return exchange.getResponse().writeWith(Mono.empty());
           }).authenticationEntryPoint((exchange, denied) -> {
               log.error("异常2 (0)", denied);
               return exchange.getResponse().writeWith(Mono.empty());
           });
        });
//        UsernamePasswordAuthenticationToken
//        UsernamePasswordAuthenticationFilter
//        http.formLogin(form -> form.
//                loginPage("/doLogin"))
//        loginProcessingUrl("/doLogin")
//                .failureForwardUrl("/error")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .authenticationDetailsSource(new MyWebAuthenticationDetailsSource(redisTemplateSO))
//                .successHandler(new SuccessHandler())
//                .failureHandler(new FailureHandler())
//        );

//        http.logout(logout ->
//                logout.invalidateHttpSession(true)
//                        .logoutUrl("/logout")
//                        .deleteCookies("Border", "rememberMe")
//                        .logoutSuccessHandler(new MyLogoutSuccessHandler())
//        );
        return http.build();
    }
}
