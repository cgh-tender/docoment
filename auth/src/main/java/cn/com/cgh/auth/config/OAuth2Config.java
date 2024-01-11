//package cn.com.cgh.auth.config;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.oauth2.client.*;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
//import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.authentication.logout.DelegatingServerLogoutHandler;
//import org.springframework.security.web.server.authentication.logout.SecurityContextServerLogoutHandler;
//import org.springframework.security.web.server.authentication.logout.WebSessionServerLogoutHandler;
//import org.springframework.util.StringUtils;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@Slf4j
//public class OAuth2Config {
////    @Bean
////    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
////        http
////                .x509(withDefaults())
////                .authorizeExchange(exchanges -> {
////                    log.info("securityWebFilterChain");
////                    exchanges
////                                    .anyExchange().permitAll();
////
////                        }
////                );
////        return http.build();
////    }
////    @Bean
////    SecurityWebFilterChain http(ServerHttpSecurity http) throws Exception {
////        DelegatingServerLogoutHandler logoutHandler = new DelegatingServerLogoutHandler(
////                new WebSessionServerLogoutHandler(), new SecurityContextServerLogoutHandler()
////        );
////        http.authorizeExchange((exchange) -> exchange.anyExchange().authenticated())
////                .logout((logout) -> logout.logoutHandler(logoutHandler));
////
////        return http.build();
////    }
//    @Bean
//    public OAuth2AuthorizedClientManager authorizedClientManager(
//            ClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientRepository authorizedClientRepository) {
//
//        OAuth2AuthorizedClientProvider authorizedClientProvider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                        .authorizationCode()
//                        .refreshToken()
//                        .clientCredentials()
//                        .password()
//                        .build();
//
//        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
//                new DefaultOAuth2AuthorizedClientManager(
//                        clientRegistrationRepository, authorizedClientRepository);
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//        authorizedClientManager.setContextAttributesMapper(contextAttributesMapper());
//
//        return authorizedClientManager;
//    }
//
//    private Function<OAuth2AuthorizeRequest, Map<String, Object>> contextAttributesMapper() {
//        return authorizeRequest -> {
//            Map<String, Object> contextAttributes = Collections.emptyMap();
//            HttpServletRequest servletRequest = authorizeRequest.getAttribute(HttpServletRequest.class.getName());
//            String username = servletRequest.getParameter(OAuth2ParameterNames.USERNAME);
//            String password = servletRequest.getParameter(OAuth2ParameterNames.PASSWORD);
//            if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
//                contextAttributes = new HashMap<>();
//
//                // `PasswordOAuth2AuthorizedClientProvider` requires both attributes
//                contextAttributes.put(OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME, username);
//                contextAttributes.put(OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME, password);
//            }
//            return contextAttributes;
//        };
//    }
//}
