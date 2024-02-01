package cn.com.cgh.romantic.server.auth;

import cn.com.cgh.gallery.util.ResponseImpl;
import cn.com.cgh.romantic.pojo.auth.AuthCheckEntity;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author cgh
 */
@FeignClient(name = "auth"
        , fallbackFactory = IAuthCheckController.TestFallbackFactory.class
        , contextId = "auth-auth"
)
public interface IAuthCheckController {

    @GetMapping("/controllerCheckAuth")
    ResponseImpl<Boolean> controllerCheckAuth(AuthCheckEntity authCheckEntity);

    @Component
    class TestFallbackFactory implements FallbackFactory<IAuthCheckController> {
        @Override
        public IAuthCheckController create(Throwable cause) {
            return null;
        }
    }
}
