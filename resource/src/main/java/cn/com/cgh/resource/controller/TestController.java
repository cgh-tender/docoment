package cn.com.cgh.resource.controller;

import cn.com.cgh.core.util.IdWork;
import cn.com.cgh.resource.auth.service.impl.TbCfgOrganizationImpl;
import cn.com.cgh.romantic.pojo.TbCfgOrganization;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/test")
@Slf4j
public class TestController {
    private final TbCfgOrganizationImpl tbCfgOrganization;

    @Autowired
    private IdWork idWork;

    @PostMapping
    public void post() {
        log.info(idWork.nextId() + "");
        TbCfgOrganization cfgOrganization = new TbCfgOrganization();
        cfgOrganization.setName("test");
        cfgOrganization.setDescription("test");
        cfgOrganization.setParentId(0L);
        tbCfgOrganization.save(cfgOrganization);
    }

    @GetMapping
    public Page<TbCfgOrganization> get() {
        return tbCfgOrganization.page(new Page<>(1, 10));
    }

    @DeleteMapping("/{id}")
    public String del(@PathVariable Long id) {
        return tbCfgOrganization.removeById(id) ? "删除成功" : "删除失败";
    }
}
