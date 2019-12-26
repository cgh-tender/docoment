package cn.com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloWordController {
    private static Logger logger = LoggerFactory.getLogger(HelloWordController.class);

    @RequestMapping("/world")
    @ResponseBody
    public String getHelloWordMedole(){
        logger.info("Hello World");
        return "Hello World";
    }

}