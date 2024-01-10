package cn.com.cgh.romantic.login;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Map;

@FeignClient(name = "login", fallback = ILoginControllerFallback.class, configuration = ILoginControllerConfiguration.class)
public interface ILoginController {

    @GetMapping("/getCode")
//    @Cacheable(cacheNames = "login", cacheManager = Constants.REDIS_CACHE_MANAGER_NAME, key = "#code")
    void getCode(HttpServletResponse response) throws IOException;
}

