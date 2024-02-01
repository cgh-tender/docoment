package cn.com.cgh.resource.error.controller;

import cn.com.cgh.resource.error.service.ITbCfgErrorService;
import cn.com.cgh.romantic.pojo.gateway.TbCfgError;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author cgh
 */
@RestController
@RequestMapping("/cfgError")
@AllArgsConstructor
public class TbCfgErrorController {
    private final ITbCfgErrorService tbCfgErrorService;
    @GetMapping("/{code}")
    public String getErrorMessage(@PathVariable String code){
        TbCfgError one = tbCfgErrorService.lambdaQuery().eq(TbCfgError::getCode, code).one();
        return Optional.ofNullable(one).map(t -> {
            Long targetCode = t.getTargetCode();
            if (targetCode == 0L){
                return t.getMessage();
            }else{
                return tbCfgErrorService.lambdaQuery().eq(TbCfgError::getCode, targetCode).one().getMessage();
            }
        }).orElseGet(null);
    }
}
