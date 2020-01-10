package cn.com.utils.ex;

import cn.com.SpringContextUtil;
import cn.com.entity.RestStatus;
import cn.com.entity.Result;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Controller
@ResponseBody
@Log4j
public class AppErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean separation = Boolean.FALSE;
        try {
            separation = SpringContextUtil.isSeparation(request);
        } catch (Exception e) {
            throw new Exception("异常 获取 Shiro 是否创建TOKEN异常: " + e.getMessage());
        }
//        log.info(">>>>>>>>>>>>>>> AppErrorController <<<<<<<<<<<<<");
        if (separation){
            int statusCode = response.getStatus();
            return statusCode+"";
        }
        int statusCode = response.getStatus();
        Result.failed("异常 [ " + statusCode + " ] ");
        return "";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
