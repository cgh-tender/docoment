package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.resource.auth.service.ITbCfgResourceService;
import cn.com.cgh.romantic.pojo.resource.TbCfgResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品销售统计 前端控制器
 * </p>
 *
 * @author cgh
 * @since 2024-01-05
 */
@RestController
@RequestMapping("/resource")
@Slf4j
@Tag(name = "商品销售统计")
public class TbCfgResourceController {
    @Autowired
    private ITbCfgResourceService ITbCfgResourceService;

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 3600, multiplier = 1.5))
    public Map call() {
        log.info("成功");
        throw new RuntimeException("获取验证码失败");
    }

    @Recover
    public Map call1(Exception e) {
        log.info("获取验证码失败 {}", e.getMessage());
        throw new RuntimeException("获取验证码失败");
    }

    @GetMapping("")
    public List<TbCfgResource> queryMenu() {
        return ITbCfgResourceService.queryTbCfgResourceList();
    }


    @PostMapping
    public String save(TbCfgResource resource) {
        boolean save = ITbCfgResourceService.save(resource);
        return save ? "保存成功" : "保存失败";
    }
}
