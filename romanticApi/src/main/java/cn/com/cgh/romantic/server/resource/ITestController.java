package cn.com.cgh.romantic.server.resource;

import cn.com.cgh.romantic.util.ResponseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author cgh
 */
@FeignClient(name = "resource"
        , fallbackFactory = ITestController.TestFallbackFactory.class
        , contextId = "resource-test"
)
public interface ITestController {
    Logger logger = LoggerFactory.getLogger(ITestController.class);

    @GetMapping("/getTest1")
    public ResponseImpl getTest();


    @Component
    class TestFallbackFactory implements FallbackFactory<ITestController> {

        @Override
        public ITestController create(Throwable cause) {
            return () -> {
                logger.info("ssssss");
                return null;
            };
        }

    }
}
