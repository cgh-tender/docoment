package cn.com.cgh.login.auth.controller;

import cn.com.cgh.login.auth.entity.SysResource;
import cn.com.cgh.login.auth.service.ISysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "SysResourceController", tags = "商品销售统计")
public class SysResourceController {
    @Autowired
    private ISysResourceService ISysResourceService;
    @GetMapping("/list")
    @ApiOperation("分页用户列表")
    public List<SysResource> querySysResourceList() {
        return ISysResourceService.query().list();
    }

}
