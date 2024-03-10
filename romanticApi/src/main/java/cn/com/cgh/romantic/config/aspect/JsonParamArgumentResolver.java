package cn.com.cgh.romantic.config.aspect;

import cn.com.cgh.romantic.config.aspect.annotation.JsonParam;
import cn.com.cgh.romantic.util.RequestUtil;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Objects;

import static cn.com.cgh.romantic.util.RequestUtil.CACHED_REQUEST_OBJECT_BODY_KEY;

public class JsonParamArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonParam.class);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        JsonParam annotation = parameter.getParameterAnnotation(JsonParam.class);
        if (annotation != null) {
            String parameterName = annotation.value();
            if (StringUtils.isBlank(parameterName)) {
                parameterName = parameter.getParameterName();
            }
            String finalParameterName = parameterName;
            return RequestUtil.getBody(exchange).mapNotNull(c -> {
                String attribute = c.getAttribute(CACHED_REQUEST_OBJECT_BODY_KEY);
                Object o;
                if (attribute != null) {
                    o = JSONUtil.parseObj(attribute).get(finalParameterName);
                } else {
                    o = c.getRequest().getQueryParams().getFirst(finalParameterName);
                }
                Class<?> objectType = annotation.objectType();
                try {
                    if (objectType != JsonParam.class) {
                        o = JSONUtil.parseArray(o.toString()).toList(objectType);
                    } else {
                        o = JSONUtil.parseObj(o).toBean(parameter.getParameterType());
                    }
                } catch (Exception e) {
                    LoggerFactory.getLogger(JsonParamArgumentResolver.class).error("参数Json转换失败:" + parameter.getContainingClass().getName() + "." + Objects.requireNonNull(parameter.getMethod()).getName() + "[" + finalParameterName + "]");
                }
                return o;
            });
        } else {
            throw new IllegalStateException("CustomHeader annotation should be JsonParam");
        }
    }
}
