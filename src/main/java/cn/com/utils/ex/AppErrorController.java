package cn.com.utils.ex;

import cn.com.entity.Result;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Configuration
@Controller
@ResponseBody
@Log4j
public class AppErrorController extends ErrorPageFilter implements ErrorController {
    private ErrorAttributes errorAttributes;

    @Autowired
    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }
    @RequestMapping(value = "/error")
    public void errorApiHandler(HttpServletRequest request, HttpServletResponse response, final Exception ex, final WebRequest req) {
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(req, false);
        int status = (int) attr.get("status");
        int code = attr.get("code") != null ? (int)attr.get("code") : 0;
        String message = (String) attr.get("message");
        Result.failed(response,code != 0 ? code : status, message);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
