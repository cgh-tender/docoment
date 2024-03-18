package cn.com.cgh.resource.auth.service;

import cn.com.cgh.romantic.pojo.SelectOption;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/dict")
public interface ITbCfgDictService {
    @GetMapping("/getUserStatus")
    public List<SelectOption> getUserStatus();
}
