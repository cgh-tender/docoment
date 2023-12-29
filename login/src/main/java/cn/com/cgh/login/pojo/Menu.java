package cn.com.cgh.login.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Menu {
    private String path;
    private String redirect;
    private String alias;
    private String name;
    private String beforeEnter;
    private String component;
    private RouteMeta meta;
    private List<Menu> children = new ArrayList<>();
}