package cn.com.cgh.core.util;

import com.alibaba.cloud.nacos.util.InetIPv6Utils;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Slf4j
public class RequestUtil {
    public static final String CACHED_REQUEST_OBJECT_BODY_KEY = "cachedRequestObjectBody";
    protected static InetUtils inetUtils;
    protected static InetIPv6Utils inetIPv6Utils;
    protected static final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    /**
     * exchange.mutate().request(request).build()
     */
    public static ServerHttpRequestDecorator getRequestBody(String value, ServerHttpRequest request) {
        DataBuffer bodyDataBuffer = stringBuffer(value);
        Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
        return new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                return bodyFlux;
            }
        };//封装我们的request
    }
    private static DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }


    public static Mono<ServerWebExchange> getBody(ServerWebExchange exchange) {
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            if (exchange.getAttribute(CACHED_REQUEST_OBJECT_BODY_KEY) != null) {
                return Mono.just(exchange);
            }
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);
            Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                DataBufferUtils.retain(buffer);
                return Mono.just(buffer);
            });
            String s = new String(bytes, StandardCharsets.UTF_8);
            log.info("get body from cache:{}", s);
            /**
             * repackage ServerHttpRequest
             */
            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };
            /**
             * mutate exchage with new ServerHttpRequest
             */
            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
            mutatedExchange.getAttributes().put(CACHED_REQUEST_OBJECT_BODY_KEY, s);
            return Mono.just(mutatedExchange);
        });
    }

    public static String getIpAddr(ServerWebExchange exchange) {
        String first = exchange.getRequest().getHeaders().getFirst("X-Real-IP");
        if (first != null && !"unknown".equalsIgnoreCase(first)) {
            return first;
        }
        return Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
    }

    /**
     * 获取本地IP地址
     *
     * @return 本地IP地址
     */
    public static String getLocalAddress() {
        if (inetUtils == null) {
            inetUtils = CoreApplication.getBean(InetUtils.class);
        }
        return inetUtils.findFirstNonLoopbackAddress().getHostAddress();
    }

    public static String getLocalIpV6Address() {
        if (inetIPv6Utils == null) {
            inetIPv6Utils = CoreApplication.getBean(InetIPv6Utils.class);
        }
        return inetIPv6Utils.findIPv6Address();
    }


    public static String getMacAddress() throws Exception {
        // 取mac地址
        byte[] macAddressBytes = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
        // 下面代码是把mac地址拼装成String
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < macAddressBytes.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(macAddressBytes[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        return sb.toString().trim().toUpperCase();
    }
}
