package cn.com.cgh.resource.auth.service.impl;

import cn.com.cgh.resource.auth.service.ITbCfgDictService;
import cn.com.cgh.romantic.em.UserStatus;
import cn.com.cgh.romantic.pojo.SelectOption;
import cn.com.cgh.romantic.util.DisplayedEnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Service
@Slf4j
public class TbCfgDictServiceImpl implements ITbCfgDictService {
    @Override
    public List<SelectOption> getUserStatus() {
        return DisplayedEnumUtil.parser(UserStatus.values());
    }
}
