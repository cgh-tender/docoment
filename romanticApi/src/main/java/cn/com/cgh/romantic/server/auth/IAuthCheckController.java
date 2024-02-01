package cn.com.cgh.romantic.server.auth;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.pojo.auth.AuthCheckEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author cgh
 */
@FeignClient(name = "auth"
        , fallback = IAuthCheckControllerFallback.class
        , configuration = IAuthCheckControllerConfiguration.class
        , contextId = "auth-auth"
)
public interface IAuthCheckController {

    @GetMapping("/controllerCheckAuth")
    ResponseImpl<Boolean> controllerCheckAuth(AuthCheckEntity authCheckEntity);

}
class IAuthCheckControllerConfiguration {
    @Bean
    public IAuthCheckControllerFallback loginControllerFallback() {
        return new IAuthCheckControllerFallback();
    }
}

class IAuthCheckControllerFallback implements IAuthCheckController {
    @Override
    public ResponseImpl<Boolean> controllerCheckAuth(AuthCheckEntity authCheckEntity) {
        return ResponseImpl.<Boolean>builder().data(Boolean.FALSE).build().FULL();
    }
}
