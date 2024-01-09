package cn.com.cgh.romantic.login;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "login", fallback = ILoginControllerFallback.class, configuration = ILoginControllerConfiguration.class)
public interface ILoginController {
    @GetMapping("/getCode")
    Map getCode(String code);
}

