package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.romantic.pojo.TbCfgResource;
import cn.com.cgh.resource.auth.service.ISysResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 商品销售统计 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2024-01-05
 */
@Controller
@RequestMapping("/sysResource")
@Tag(name = "商品销售统计")
public class SysResourceController {
    @Autowired
    private ISysResourceService ISysResourceService;
    @GetMapping("/list")
    @Operation(summary = "分页用户列表")
    public List<TbCfgResource> querySysResourceList() {
        return ISysResourceService.query().list();
    }

}
