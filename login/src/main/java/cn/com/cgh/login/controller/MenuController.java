package cn.com.cgh.login.controller;

import cn.com.cgh.login.pojo.Menu;
import cn.com.cgh.login.pojo.RouteMeta;
import cn.com.cgh.romantic.login.IMenuController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class MenuController implements IMenuController<Menu> {
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

//    ,
//    {
//        path: "/unocss",
//                component: Layouts,
//            redirect: "/unocss/index",
//            children: [
//        {
//            path: "index",
//                    component: () => import("@/views/unocss/index.vue"),
//                name: "UnoCSS",
//                meta: {
//            title: "UnoCSS",
//                    svgIcon: "unocss"
//        }
//        }
//    ]
//    },
//    {
//        path: "/link",
//                meta: {
//        title: "外链",
//                svgIcon: "link"
//    },
//        children: [
//        {
//            path: "https://juejin.cn/post/7089377403717287972",
//                    component: () => {},
//            name: "Link1",
//                    meta: {
//            title: "中文文档"
//        }
//        },
//        {
//            path: "https://juejin.cn/column/7207659644487139387",
//                    component: () => {},
//            name: "Link2",
//                    meta: {
//            title: "新手教程"
//        }
//        }
//    ]
//    },
//    {
//        path: "/table",
//                component: Layouts,
//            redirect: "/table/element-plus",
//            name: "Table",
//            meta: {
//        title: "表格",
//                elIcon: "Grid"
//    },
//        children: [
//        {
//            path: "element-plus",
//                    component: () => import("@/views/table/element-plus/index.vue"),
//                name: "ElementPlus",
//                meta: {
//            title: "Element Plus",
//                    keepAlive: true
//        }
//        },
//        {
//            path: "vxe-table",
//                    component: () => import("@/views/table/vxe-table/index.vue"),
//                name: "VxeTable",
//                meta: {
//            title: "Vxe Table",
//                    keepAlive: true
//        }
//        }
//    ]
//    },
//    {
//        path: "/menu",
//                component: Layouts,
//            redirect: "/menu/menu1",
//            name: "Menu",
//            meta: {
//        title: "多级路由",
//                svgIcon: "menu"
//    },
//        children: [
//        {
//            path: "menu1",
//                    component: () => import("@/views/menu/menu1/index.vue"),
//                redirect: "/menu/menu1/menu1-1",
//                name: "Menu1",
//                meta: {
//            title: "menu1"
//        },
//            children: [
//            {
//                path: "menu1-1",
//                        component: () => import("@/views/menu/menu1/menu1-1/index.vue"),
//                    name: "Menu1-1",
//                    meta: {
//                title: "menu1-1",
//                        keepAlive: true
//            }
//            },
//            {
//                path: "menu1-2",
//                        component: () => import("@/views/menu/menu1/menu1-2/index.vue"),
//                    redirect: "/menu/menu1/menu1-2/menu1-2-1",
//                    name: "Menu1-2",
//                    meta: {
//                title: "menu1-2"
//            },
//                children: [
//                {
//                    path: "menu1-2-1",
//                            component: () => import("@/views/menu/menu1/menu1-2/menu1-2-1/index.vue"),
//                        name: "Menu1-2-1",
//                        meta: {
//                    title: "menu1-2-1",
//                            keepAlive: true
//                }
//                },
//                {
//                    path: "menu1-2-2",
//                            component: () => import("@/views/menu/menu1/menu1-2/menu1-2-2/index.vue"),
//                        name: "Menu1-2-2",
//                        meta: {
//                    title: "menu1-2-2",
//                            keepAlive: true
//                }
//                }
//            ]
//            },
//            {
//                path: "menu1-3",
//                        component: () => import("@/views/menu/menu1/menu1-3/index.vue"),
//                    name: "Menu1-3",
//                    meta: {
//                title: "menu1-3",
//                        keepAlive: true
//            }
//            }
//        ]
//        },
//        {
//            path: "menu2",
//                    component: () => import("@/views/menu/menu2/index.vue"),
//                name: "Menu2",
//                meta: {
//            title: "menu2",
//                    keepAlive: true
//        }
//        }
//    ]
//    },
//    {
//        path: "/hook-demo",
//                component: Layouts,
//            redirect: "/hook-demo/use-fetch-select",
//            name: "HookDemo",
//            meta: {
//        title: "Hook 示例",
//                elIcon: "Menu",
//                alwaysShow: true
//    },
//        children: [
//        {
//            path: "use-fetch-select",
//                    component: () => import("@/views/hook-demo/use-fetch-select.vue"),
//                name: "UseFetchSelect",
//                meta: {
//            title: "useFetchSelect"
//        }
//        },
//        {
//            path: "use-fullscreen-loading",
//                    component: () => import("@/views/hook-demo/use-fullscreen-loading.vue"),
//                name: "UseFullscreenLoading",
//                meta: {
//            title: "useFullscreenLoading"
//        }
//        },
//        {
//            path: "use-watermark",
//                    component: () => import("@/views/hook-demo/use-watermark.vue"),
//                name: "UseWatermark",
//                meta: {
//            title: "useWatermark"
//        }
//        }
//    ]
//    }
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
    }
    @Override
    @GetMapping("/getMenu")
    public List<Menu> queryMenu(){
        return asyncRoutes;
    }

}
