package cn.com.cgh.romantic.login;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@FeignClient(name = "login", fallback = ILoginControllerFallback.class, configuration = ILoginControllerConfiguration.class)
public interface ILoginController {

    @GetMapping("/getCode")
//    @Cacheable(cacheNames = "login", cacheManager = Constants.REDIS_CACHE_MANAGER_NAME, key = "#code")
    void getCode(HttpServletResponse response) throws IOException;
}

class ILoginControllerConfiguration {
    @Bean
    public ILoginControllerFallback loginControllerFallback() {
        return new ILoginControllerFallback();
    }
}

class ILoginControllerFallback implements ILoginController {
    @Override
    public void getCode(HttpServletResponse response) {

    }
}
