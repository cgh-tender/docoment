package cn.com.utils.ex;

import cn.com.SpringContextUtil;
import cn.com.entity.Result;
import cn.com.utils.AuthFilterItemProperties;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Configuration
@Controller
@ResponseBody
@Log4j
public class AppErrorController extends ErrorPageFilter implements ErrorController {
    private ErrorAttributes errorAttributes;

    private AuthFilterItemProperties authFilterItemProperties;

    @Autowired
    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }
    @Override
    protected String getDescription(HttpServletRequest request) {
//        log.info("AppErrorController getDescription");
        return super.getDescription(request);
    }

    public AppErrorController() {
        super();
//        log.info("AppErrorController AppErrorController");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
//        log.info("AppErrorController init");
    }

    @Override

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
        log.info(((HttpServletRequest) request).getHeader("separation"));
//        log.info("AppErrorController doFilter");
    }

    @Override
    public void addErrorPages(ErrorPage... errorPages) {
        super.addErrorPages(errorPages);
//        log.info("AppErrorController addErrorPages");
    }

    @Override
    public void destroy() {
        super.destroy();
//        log.info("AppErrorController destroy");
    }

    @RequestMapping(value = "/error")
    public String errorApiHandler(HttpServletRequest request, HttpServletResponse response, final Exception ex, final WebRequest req) {
        if (authFilterItemProperties == null)authFilterItemProperties = SpringContextUtil.getBean(AuthFilterItemProperties.class);
        Map<String, Object> attr = this.errorAttributes.getErrorAttributes(req, false);
        int status = (int) attr.get("status");
        log.error(">>>>>>>>>>>>>>> " + (attr.get("message") == "No message available" ? "" : attr.get("message") ) + " <<<<<<<<<<<<<");
        boolean separation = SpringContextUtil.isSeparation(response);
        if (separation){
            String message = (String) attr.get("message");
            if (message.contains("请重新登录")){
                return authFilterItemProperties.getLOGIN();
            }
            int statusCode = response.getStatus();
            return statusCode+"";
        }

        if (status == 404){
            Result.failed("path: "  + attr.get("path") + " 请求,异常 : 资源不存在");
        }else if(status == 302){
            Result.failed("path: "  + attr.get("path") + " 请求,异常 : ");
        }else {
            Result.failed("path: "  + attr.get("path") + " 请求,异常 :" + attr.get("message"));
        }
        return "";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
