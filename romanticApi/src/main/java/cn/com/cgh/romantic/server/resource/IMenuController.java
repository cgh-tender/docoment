package cn.com.cgh.romantic.server.resource;

import cn.com.cgh.romantic.util.ResponseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.util.List;

@FeignClient(name = "resource"
        , fallbackFactory = IMenuController.TestFallbackFactory.class
        , contextId = "resource-menu"
)
public interface IMenuController {
    Logger logger = LoggerFactory.getLogger(IMenuController.class);

    @GetMapping("")
    Mono<ResponseImpl<List>> queryMenu();


    @Component
    class TestFallbackFactory<T> implements FallbackFactory<IMenuController> {

        @Override
        public IMenuController create(Throwable cause) {
            return () -> {
                logger.info("ssssss");
                return null;
            };
        }

    }
}
