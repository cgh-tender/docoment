package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.resource.auth.service.ITbCfgResourceService;
import cn.com.cgh.resource.pojo.Menu;
import cn.com.cgh.resource.pojo.RouteMeta;
import cn.com.cgh.romantic.pojo.TbCfgResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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
@Controller
@RequestMapping("/resource")
@Slf4j
@Tag(name = "商品销售统计")
public class TbCfgResourceController {
    @Autowired
    private TbCfgResourceController tbCfgResourceController;
    @Autowired
    private ITbCfgResourceService ITbCfgResourceService;
    @GetMapping("/list")
    @Operation(summary = "分页用户列表")
    public List<TbCfgResource> queryTbCfgResourceList() {
        return ITbCfgResourceService.query().list();
    }

    @Retryable(retryFor = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 3600,multiplier = 1.5))
    public Map call(){
        log.info("成功");
        throw new RuntimeException("获取验证码失败");
    }

    @Recover
    public Map call1(Exception e){
        log.info("获取验证码失败 {}",e.getMessage());
        throw new RuntimeException("获取验证码失败");
    }

    private static final List<Menu> asyncRoutes = new ArrayList<>();
    static {
        Menu asyMenu = new Menu();
        asyncRoutes.add(asyMenu);
        asyMenu.setPath("/permission");
        asyMenu.setName("Permission");
        asyMenu.setRedirect("/permission/page");
        asyMenu.setComponent("Layouts");
        asyMenu.setMeta(RouteMeta.builder().title("权限管理").svgIcon("lock").roles(new String[]{"admin", "editor"}).alwaysShow(true).build());
        Menu menu = new Menu();
        menu.setPath("page");
        menu.setName("PagePermission");
        menu.setComponent("permission/page");
        menu.setKeepAlive(Boolean.TRUE);
        menu.setMeta(RouteMeta.builder().title("页面权限").roles(new String[]{"admin"}).build());
        asyMenu.getChildren().add(menu);
        menu = new Menu();
        menu.setPath("directive");
        menu.setName("DirectivePermission");
        menu.setComponent("permission/directive");
        menu.setKeepAlive(Boolean.TRUE);
        menu.setMeta(RouteMeta.builder().title("指令权限").roles(new String[]{"admin","editor"}).build());
        asyMenu.getChildren().add(menu);
        menu = new Menu();
        menu.setPath("directive");
        menu.setName("umsUser");
        menu.setComponent("permission/directive");
        menu.setKeepAlive(Boolean.TRUE);
        menu.setMeta(RouteMeta.builder().title("指令权限1").roles(new String[]{"admin","editor"}).build());
        asyMenu.getChildren().add(menu);
    }
    @GetMapping("/getMenu")
    public List<Menu> queryMenu(){
        return asyncRoutes;
    }

}
