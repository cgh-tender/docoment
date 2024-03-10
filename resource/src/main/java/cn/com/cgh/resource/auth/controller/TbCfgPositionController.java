package cn.com.cgh.resource.auth.controller;

import cn.com.cgh.romantic.config.aspect.annotation.JsonParam;
import cn.com.cgh.romantic.pojo.resource.TbCfgPosition;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 职位表 前端控制器
 * </p>
 *
 * @author cgh
 * @since 2024-01-24
 */
@RestController
@RequestMapping("/tbCfgPosition")
public class TbCfgPositionController {
    @RequestMapping
    public String test(@JsonParam TbCfgPosition tbCfgPosition){
        System.out.println(tbCfgPosition);
        return "success";
    }
}
