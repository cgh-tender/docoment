package cn.com.cgh.resource;

import cn.com.cgh.resource.auth.service.impl.TbCfgGroupServiceImpl;
import cn.com.cgh.resource.auth.service.impl.TbCfgResourceServiceImpl;
import cn.com.cgh.romantic.pojo.resource.TbCfgResource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class ResourceTest {
    @Autowired
    private TbCfgResourceServiceImpl tbCfgResourceServiceImpl;
    @Autowired
    private TbCfgGroupServiceImpl tbCfgGroupService;
    @Test
    public void test() {
        List<TbCfgResource> tbCfgResources = tbCfgResourceServiceImpl.queryTbCfgResourceList();
        System.out.println(1);
    }
}
