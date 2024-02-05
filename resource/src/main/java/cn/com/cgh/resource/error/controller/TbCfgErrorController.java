package cn.com.cgh.resource.error.controller;

import cn.com.cgh.resource.error.service.ITbCfgErrorService;
import cn.com.cgh.romantic.pojo.gateway.TbCfgError;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.util.Assert.isNull;

/**
 * @author cgh
 */
@RestController
@RequestMapping("/cfgError")
@AllArgsConstructor
public class TbCfgErrorController {
    private final ITbCfgErrorService tbCfgErrorService;

    @GetMapping("/{code}")
    public String getErrorMessage(@PathVariable Long code) {
        TbCfgError one = tbCfgErrorService.lambdaQuery().eq(TbCfgError::getCode, code).one();
        return Optional.ofNullable(one).map(t -> {
            Long targetCode = t.getTargetCode();
            if (targetCode == 0L) {
                return t.getMessage();
            } else {
                return tbCfgErrorService.lambdaQuery().eq(TbCfgError::getCode, targetCode).one().getMessage();
            }
        }).orElseGet(() -> code + " 当前状态码不存在 ");
    }

    @PostMapping("/{code}")
    public String saeMessage(Long targetCode, String message, @PathVariable Long code) {
        TbCfgError one = tbCfgErrorService.lambdaQuery().eq(TbCfgError::getCode, code).one();
        isNull(one, "code【" + code + "】响应异常结果是：【" + (one == null ? null : one.getMessage()) + "】");
        TbCfgError build = TbCfgError.builder().code(code).message(message).targetCode(targetCode).build();
        boolean save = tbCfgErrorService.save(build);
        return save ? "保存成功" : "保存失败";
    }
}
