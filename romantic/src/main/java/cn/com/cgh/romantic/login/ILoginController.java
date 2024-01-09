package cn.com.cgh.romantic.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "login", fallbackFactory = ILoginController.TestFallbackFactory.class)
public interface ILoginController {
    Logger logger = LoggerFactory.getLogger(ILoginController.class);

    @GetMapping("/getCode")
//    @SentinelResource(value = cn.com.cgh.romantic.config.Constants.ONE_RULE,blockHandler = "handler",blockHandlerClass = cn.com.cgh.romantic.sentinelRule.CustomerBlockHandler.class)
    Map getCode(String code);


    @Component
    static class TestFallbackFactory implements FallbackFactory<ILoginController> {

        @Override
        public ILoginController create(Throwable cause) {
            return new ILoginController() {
                @Override
                public Map getCode(String code) {
                    logger.info("返回的信息为{}", code);
                    return null;
                }
            };
        }

    }
}
