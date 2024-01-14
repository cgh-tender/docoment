package cn.com.cgh.core.advice;

import cn.com.cgh.core.util.ResponseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 非空返回值的处理.
 *
 * @author <a href="mailto:943868899@qq.com">Yujie</a>
 * @version 0.1
 * @since 0.1
 */
@ConditionalOnClass(ResponseBodyAdvice.class)
@ControllerAdvice
@Order(value = 1000)
public class NotVoidResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final Logger logger = LoggerFactory.getLogger(NotVoidResponseBodyAdvice.class);
    /**
     * 路径过滤器
     */
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    /**
     * 只处理不返回void的，并且MappingJackson2HttpMessageConverter支持的类型.
     *
     * @param methodParameter 方法参数
     * @param clazz           处理器
     * @return 是否支持
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> clazz) {
        Method method = methodParameter.getMethod();

        //method为空、返回值为void、非JSON，直接跳过
        if (Objects.isNull(method)
                || method.getReturnType().equals(Void.TYPE)
                || !MappingJackson2HttpMessageConverter.class.isAssignableFrom(clazz)) {
            logger.debug("Graceful Response:method为空、返回值为void、非JSON，跳过");
            return false;
        }
        logger.debug("Graceful Response:非空返回值，需要进行封装");
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> clazz,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (body == null) {
            return ResponseImpl.builder().build().SUCCESS();
        } else {
            if (logger.isDebugEnabled()) {
                String path = serverHttpRequest.getURI().getPath();
                logger.debug("Graceful Response:非空返回值，执行封装:path={}", path);
            }
            return ResponseImpl.builder().data(body).build().SUCCESS();
        }
    }

}
