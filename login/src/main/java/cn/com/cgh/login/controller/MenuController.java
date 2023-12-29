package cn.com.cgh.login.controller;

import cn.com.cgh.login.pojo.Menu;
import cn.com.cgh.login.pojo.RouteMeta;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class MenuController {
    private static final List<Menu> asyncRoutes = new ArrayList<>();
    static {
        Menu asyMenu = new Menu();
        asyncRoutes.add(asyMenu);

        asyMenu.setPath("/permission");
        asyMenu.setName("Permission");
        asyMenu.setRedirect("/permission/page");
        asyMenu.setMeta(RouteMeta.builder().title("权限管理").svgIcon("lock").roles(new String[]{"admin", "editor"}).alwaysShow(true).build());
        Menu menu = new Menu();
        menu.setPath("page");
        menu.setName("PagePermission");
        menu.setComponent("/views/permission/page");
        menu.setMeta(RouteMeta.builder().title("页面权限").roles(new String[]{"admin"}).build());
        asyMenu.getChildren().add(menu);
        menu = new Menu();
        menu.setPath("directive");
        menu.setName("DirectivePermission");
        menu.setComponent("/views/permission/directive");
        menu.setMeta(RouteMeta.builder().title("指令权限").roles(new String[]{"admin"}).build());
        asyMenu.getChildren().add(menu);
    }
    @GetMapping("/getMenu")
    public Map<String,Object> queryMenu(){

        return null;
    }

}
