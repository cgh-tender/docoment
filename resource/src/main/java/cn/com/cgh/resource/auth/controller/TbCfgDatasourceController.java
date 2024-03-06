package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.romantic.server.resource.ITestController;
import cn.com.cgh.romantic.util.ResponseImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 数据库连接表 前端控制器
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RestController
@RequestMapping("/tbCfgDatasource")
@Slf4j
public class TbCfgDatasourceController {
    @Autowired
    private ITestController testController;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/getTest")
    public Mono<ResponseImpl> getTest()
    {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> testController.getTest(), threadPoolTaskExecutor));
    }
}
