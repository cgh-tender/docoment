package cn.com.cgh.romantic.server.resource;

import cn.com.cgh.romantic.util.ResponseImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author cgh
 */
@FeignClient(name = "resource"
        , fallback = IResourceErrorControllerFallback.class
        , configuration = IResourceErrorControllerConfiguration.class
        , contextId = "resource-error"
)
public interface IResourceErrorController {

    @GetMapping("/cfgError/{code}")
    public ResponseImpl<String> getErrorMessage(@PathVariable Long code);
}

class IResourceErrorControllerConfiguration {
    @Bean
    public ILoginControllerFallback loginControllerFallback() {
        return new ILoginControllerFallback();
    }
}

class IResourceErrorControllerFallback implements IResourceErrorController {
    @Override
    public ResponseImpl getErrorMessage(@PathVariable Long code) {
        return null;
    }
}
