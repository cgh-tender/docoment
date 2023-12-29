package cn.com.cgh.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {

    @GetMapping("/getCode")
    public Map login(){
        Map<String,String> map = new HashMap();
        map.put("url","http://dummyimage.com/100x40/dcdfe6/000000.png&text=1");
        return map;
    }
}
