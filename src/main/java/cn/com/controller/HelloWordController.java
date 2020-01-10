package cn.com.controller;

import cn.com.utils.AuthFilterItemProperties;
import cn.com.utils.testPropertite;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/hello")
@Log4j
public class HelloWordController {
    @Resource
    private testPropertite properties;
    @Resource
    private AuthFilterItemProperties authFilterItemProperties;

    @RequestMapping("/world")
    @ResponseBody
    public String getHelloWordMedole(){
//        log.info(authFilterItemProperties.toString());
        return "Hello World";
    }

}