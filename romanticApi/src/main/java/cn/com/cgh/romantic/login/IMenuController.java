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
public interface IMenuController<T> {
    Logger logger = LoggerFactory.getLogger(IMenuController.class);

    @GetMapping("/getMenu")
    public List<T> queryMenu();


    @Component
    class TestFallbackFactory<T> implements FallbackFactory<IMenuController<T>> {

        @Override
        public IMenuController<T> create(Throwable cause) {
            return new IMenuController() {
                @Override
                public List queryMenu() {
                    logger.info("ssssss");
                    return null;
                }
            };
        }

    }
}
