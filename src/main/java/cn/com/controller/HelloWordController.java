package cn.com.controller;

import cn.com.utils.AuthFilterItemProperties;
import cn.com.utils.testPropertite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/hello")
public class HelloWordController {
    private static Logger logger = LoggerFactory.getLogger(HelloWordController.class);

    @Resource
    private testPropertite properties;
    @Resource
    private AuthFilterItemProperties authFilterItemProperties;

    @RequestMapping("/world")
    @ResponseBody
    public String getHelloWordMedole(){
        logger.info("Hello World");
        logger.info(properties.getCode());
        logger.info(properties.getName());
        logger.info(authFilterItemProperties.toString());
        return "Hello World";
    }

}