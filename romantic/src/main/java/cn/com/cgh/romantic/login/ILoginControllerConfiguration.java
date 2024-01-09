package cn.com.cgh.romantic.login;

import org.springframework.context.annotation.Bean;
 class ILoginControllerConfiguration {
    @Bean
    public ILoginControllerFallback loginControllerFallback() {
        return new ILoginControllerFallback();
    }
}
