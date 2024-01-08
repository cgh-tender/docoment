package cn.com.cgh.romantic.login;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "login", fallbackFactory = ILoginController.TestFallbackFactory.class)
public interface ILoginController {

    @GetMapping("/getCode")
    Map getCode(String code);


    @Component
    static class TestFallbackFactory implements FallbackFactory<FallbackWithFactory> {

        @Override
        public FallbackWithFactory create(Throwable cause) {
            return new FallbackWithFactory();
        }

    }

    static class FallbackWithFactory implements ILoginController {
        @Override
        public Map getCode(String code) {
            return null;
        }
    }
}
