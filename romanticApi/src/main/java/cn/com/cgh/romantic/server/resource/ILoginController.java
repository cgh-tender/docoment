package cn.com.cgh.romantic.server.resource;

import cn.com.cgh.romantic.pojo.resource.TbCfgUser;
import cn.com.cgh.romantic.util.ResponseImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author cgh
 */
@FeignClient(name = "resource"
        , fallback = ILoginControllerFallback.class
        , configuration = ILoginControllerConfiguration.class
        , contextId = "resource-login"
)
public interface ILoginController {

    @GetMapping("/login")
//    @Cacheable(cacheNames = "login", cacheManager = Constants.REDIS_CACHE_MANAGER_NAME, key = "#code")
    public ResponseImpl<Map> login(@RequestBody TbCfgUser user);

    @GetMapping("/user/loadByUsername/{username}")
    ResponseImpl<TbCfgUser> loadUserByUsername(@PathVariable String username);
}

class ILoginControllerConfiguration {
    @Bean
    public ILoginControllerFallback loginControllerFallback() {
        return new ILoginControllerFallback();
    }
}

class ILoginControllerFallback implements ILoginController {
    @Override
    public ResponseImpl<Map> login(TbCfgUser user) {
        return null;
    }

    @Override
    public ResponseImpl<TbCfgUser> loadUserByUsername(String username) {
        return null;
    }
}
