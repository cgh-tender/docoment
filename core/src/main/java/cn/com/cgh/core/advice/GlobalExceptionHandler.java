package cn.com.cgh.core.advice;

import cn.com.cgh.core.util.ResponseImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.util.Map;

@ControllerAdvice
@Controller
@RequestMapping
public class GlobalExceptionHandler extends AbstractErrorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private InetUtils inetUtils;

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, InetUtils inetUtils) {
        super(errorAttributes);
        this.inetUtils = inetUtils;
    }

    @Value("${server.error.path:${error.path:/error}}")
    private static String errorProperties = "/error";


    /**
     * sql异常
     *
     * @param req
     * @param rsp
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public ResponseImpl sqlException(HttpServletRequest req, HttpServletResponse rsp, Exception ex) {

        LOGGER.error("!!! request uri:{} from {} server exception:{}", req.getRequestURI(), inetUtils.findFirstNonLoopbackAddress().getHostAddress(), ex == null ? null : ex);
        return ResponseImpl.builder().code("1002").msg(ex == null ? null : ex.getMessage()).build();
    }


    /**
     * 500错误.
     *
     * @param req
     * @param rsp
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseImpl serverError(HttpServletRequest req, HttpServletResponse rsp, Exception ex) throws Exception {
        LOGGER.error("!!! request uri:{} from {} server exception:{}", req.getRequestURI(), inetUtils.findFirstNonLoopbackAddress().getHostAddress(), ex == null ? null : ex);
        return ResponseImpl.builder().code("1002").msg(ex == null ? null : ex.getMessage()).build();
    }


    /**
     * 404的拦截.
     *
     * @param request
     * @param response
     * @param ex
     * @return
     * @throws Exception
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseImpl notFound(HttpServletRequest request, HttpServletResponse response, Exception ex) throws Exception {
        LOGGER.error("!!! request uri:{} from {} not found exception:{}", request.getRequestURI(), inetUtils.findFirstNonLoopbackAddress().getHostAddress(), ex);
        return ResponseImpl.builder().code("404").msg(ex == null ? null : ex.getMessage()).build();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseImpl paramException(MissingServletRequestParameterException ex) {
        LOGGER.error("缺少请求参数:{}", ex.getMessage());
        return ResponseImpl.builder().code("99999").msg("缺少参数:" + ex.getParameterName()).build();
    }

    //参数类型不匹配
    //getPropertyName()获取数据类型不匹配参数名称
    //getRequiredType()实际要求客户端传递的数据类型
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public ResponseImpl requestTypeMismatch(TypeMismatchException ex) {
        LOGGER.error("参数类型有误:{}", ex.getMessage());
        return ResponseImpl.builder().code("99999").msg("参数类型不匹配,参数" + ex.getPropertyName() + "类型应该为" + ex.getRequiredType()).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseImpl requestMethod(HttpRequestMethodNotSupportedException ex) {
        LOGGER.error("请求方式有误：{}", ex.getMethod());
        return ResponseImpl.builder().code("99999").msg("请求方式有误:" + ex.getMethod()).build();
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public ResponseImpl fileSizeLimit(MultipartException m) {
        LOGGER.error("超过文件上传大小限制");
        if (m.getCause() != null) {
            LOGGER.error("超过文件上传大小限制:" + m.getCause().getMessage());
        }
        return ResponseImpl.builder().code("99999").msg("超过文件大小限制,最大10MB").build();
    }


    /**
     * 重写/error请求, ${server.error.path:${error.path:/error}} IDEA报红无需处理，作用是获取spring底层错误拦截
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "${server.error.path:${error.path:/error}}")
    public ResponseImpl handleErrors(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NOT_FOUND) {
            throw new NoHandlerFoundException(request.getMethod(), request.getRequestURL().toString(), new HttpHeaders());
        }
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        return ResponseImpl.builder().code(String.valueOf(body.get("status"))).msg(String.valueOf(body.get("message"))).build();
    }


    protected String getErrorProperties() {
        return this.errorProperties;
    }
}
