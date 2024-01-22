package cn.com.cgh.romantic.login;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.pojo.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "login"
        , fallback = ILoginControllerFallback.class
        , configuration = ILoginControllerConfiguration.class
        , contextId = "login-0"
)
public interface ILoginController {

    @GetMapping("/login")
//    @Cacheable(cacheNames = "login", cacheManager = Constants.REDIS_CACHE_MANAGER_NAME, key = "#code")
    public ResponseImpl<Map> login(@RequestBody UserDto user);

}

class ILoginControllerConfiguration {
    @Bean
    public ILoginControllerFallback loginControllerFallback() {
        return new ILoginControllerFallback();
    }
}

class ILoginControllerFallback implements ILoginController {
    @Override
    public ResponseImpl<Map> login(@RequestBody UserDto user) {
        return null;
    }
}
