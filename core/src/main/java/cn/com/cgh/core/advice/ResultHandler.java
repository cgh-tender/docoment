package cn.com.cgh.core.advice;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import org.reactivestreams.Publisher;
import org.springframework.core.*;
import org.springframework.core.codec.Hints;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.NotAcceptableStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResultHandler extends ResponseBodyResultHandler {
    /**
     * 忽略特殊处理的数据类型
     */
    private static final List<Class<?>> IGNORE = new ArrayList<>();
    static {
        IGNORE.add(String.class);
    }

    public ResultHandler(List<HttpMessageWriter<?>> writers,
                         RequestedContentTypeResolver resolver, ReactiveAdapterRegistry registry) {
        super(writers, resolver, registry);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes", "ConstantConditions"})
    protected Mono<Void> writeBody(@Nullable Object body, MethodParameter bodyParameter,
                                   @Nullable MethodParameter actualParam, ServerWebExchange exchange) {

        ResolvableType bodyType = ResolvableType.forMethodParameter(bodyParameter);
        ResolvableType actualType = (actualParam != null ? ResolvableType.forMethodParameter(actualParam) : bodyType);
        ReactiveAdapter adapter = getAdapterRegistry().getAdapter(bodyType.resolve(), body);

        Publisher<?> publisher;
        ResolvableType elementType;
        ResolvableType actualElementType;
        if (adapter != null) {
            publisher = adapter.toPublisher(body);
            boolean isUnwrapped = KotlinDetector.isSuspendingFunction(bodyParameter.getMethod()) &&
                    !COROUTINES_FLOW_CLASS_NAME.equals(bodyType.toClass().getName());
            ResolvableType genericType = isUnwrapped ? bodyType : bodyType.getGeneric();
            elementType = getElementType(genericType);
            actualElementType = elementType;
        } else {
            publisher = Mono.justOrEmpty(body);
            actualElementType = body != null ? ResolvableType.forInstance(body) : bodyType;
            elementType = (bodyType.toClass() == Object.class && body != null ? actualElementType : bodyType);
        }

        MediaType bestMediaType;
        try {
            bestMediaType = selectMediaType(exchange, () -> getMediaTypesFor(elementType));
        } catch (NotAcceptableStatusException ex) {
            HttpStatusCode statusCode = exchange.getResponse().getStatusCode();
            if (statusCode != null && statusCode.isError()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Ignoring error response content (if any). " + ex.getReason());
                }
                return Mono.empty();
            }
            throw ex;
        }
        if (bestMediaType != null) {
            String logPrefix = exchange.getLogPrefix();
            if (logger.isDebugEnabled()) {
                logger.debug(logPrefix +
                        (publisher instanceof Mono ? "0..1" : "0..N") + " [" + elementType + "]");
            }
            for (HttpMessageWriter<?> writer : getMessageWriters()) {
                if (writer.canWrite(actualElementType, bestMediaType)) {
                    return writer.write((Publisher) publisher, actualType, elementType,
                            bestMediaType, exchange.getRequest(), exchange.getResponse(),
                            Hints.from(Hints.LOG_PREFIX_HINT, logPrefix));
                }
            }
        }

        MediaType contentType = exchange.getResponse().getHeaders().getContentType();
        boolean isPresentMediaType = (contentType != null && contentType.equals(bestMediaType));
        Set<MediaType> producibleTypes = exchange.getAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE);
        if (isPresentMediaType || !CollectionUtils.isEmpty(producibleTypes)) {
            return Mono.error(new HttpMessageNotWritableException(
                    "No Encoder for [" + elementType + "] with preset Content-Type '" + contentType + "'"));
        }

        List<MediaType> mediaTypes = getMediaTypesFor(elementType);
        if (bestMediaType == null && mediaTypes.isEmpty()) {
            return Mono.error(new IllegalStateException("No HttpMessageWriter for " + elementType));
        }

        return Mono.error(new NotAcceptableStatusException(mediaTypes));
    }

    private ResolvableType getElementType(ResolvableType genericType) {
        // 去除了空值和String
        if (genericType == ResolvableType.NONE || IGNORE.contains(genericType.resolve())) {
            return ResolvableType.forClass(Object.class);
        }
        return genericType;
    }

    private List<MediaType> getMediaTypesFor(ResolvableType elementType) {
        List<MediaType> writableMediaTypes = new ArrayList<>();
        for (HttpMessageWriter<?> converter : getMessageWriters()) {
            if (converter.canWrite(elementType, null)) {
                writableMediaTypes.addAll(converter.getWritableMediaTypes(elementType));
            }
        }
        return writableMediaTypes;
    }
}