package cn.com.cgh.romantic.server.auth;

import cn.com.cgh.romantic.pojo.auth.AuthCheckEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.context.annotation.Bean;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author cgh
 */
@FeignClient(name = "auth"
        , fallback = IAuthCheckControllerFallback.class
        , configuration = IAuthCheckControllerConfiguration.class
        , contextId = "auth-auth"
)
public interface IAuthCheckController {

    @PostMapping("/controllerCheckAuth")
    Boolean controllerCheckAuth(@SpringQueryMap AuthCheckEntity authCheckEntity,@RequestHeader MultiValueMap<String, String> headers);
    @GetMapping("/test")
    String test();

}
class IAuthCheckControllerConfiguration {
    @Bean
    public IAuthCheckControllerFallback loginControllerFallback() {
        return new IAuthCheckControllerFallback();
    }
}

class IAuthCheckControllerFallback implements IAuthCheckController {
    @Override
    public Boolean controllerCheckAuth(AuthCheckEntity authCheckEntity,@RequestHeader MultiValueMap<String, String> headers) {
        return Boolean.FALSE;
    }

    @Override
    public String test() {
        return "error";
    }
}
