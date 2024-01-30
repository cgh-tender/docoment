package cn.com.cgh.romantic.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "resource"
        , fallbackFactory = IMenuController.TestFallbackFactory.class
        , contextId = "resource-1"
)
public interface IMenuController {
    Logger logger = LoggerFactory.getLogger(IMenuController.class);

    @GetMapping("")
    List queryMenu();


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
