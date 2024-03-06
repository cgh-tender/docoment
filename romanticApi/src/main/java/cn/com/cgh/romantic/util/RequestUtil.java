package cn.com.cgh.romantic.util;

import com.alibaba.cloud.nacos.util.InetIPv6Utils;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author cgh
 */
@Slf4j
public class RequestUtil {
    public static final String CACHED_REQUEST_OBJECT_BODY_KEY = "cachedRequestObjectBody";
    protected static InetUtils inetUtils;
    protected static InetIPv6Utils inetIPv6Utils;
    protected static final List<HttpMessageReader<?>> MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();

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
             * repackage request
             */
            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };
            /**
             * mutate exchage with new request
             */
            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
            mutatedExchange.getAttributes().put(CACHED_REQUEST_OBJECT_BODY_KEY, s);
            return Mono.just(mutatedExchange);
        }).switchIfEmpty(Mono.just(exchange));
    }


    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST = "127.0.0.1";
    private static final String SEPARATOR = ",";

    private static final String HEADER_X_FORWARDED_FOR = "x-forwarded-for";
    private static final String HEADER_PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String HEADER_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    public static String getIpAddr(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String ipAddress = request.getHeaders().getFirst("X-Real-IP");
        if (ipAddress != null && !UNKNOWN.equalsIgnoreCase(ipAddress)) {
            return ipAddress;
        }
        try {
            // 1.根据常见的代理服务器转发的请求ip存放协议，从请求头获取原始请求ip。值类似于203.98.182.163, 203.98.182.163
            ipAddress = request.getHeaders().getFirst(HEADER_X_FORWARDED_FOR);
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeaders().getFirst(HEADER_PROXY_CLIENT_IP);
            }
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeaders().getFirst(HEADER_WL_PROXY_CLIENT_IP);
            }
            // 2.如果没有转发的ip，则取当前通信的请求端的ip
            if (StringUtils.isEmpty(ipAddress) || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                InetSocketAddress inetSocketAddress = request.getRemoteAddress();
                if(inetSocketAddress != null) {
                    ipAddress = inetSocketAddress.getAddress().getHostAddress();
                }
                // 如果是127.0.0.1，则取本地真实ip
                if (LOCALHOST.equals(ipAddress)) {
                    ipAddress = getLocalAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***"
            if (ipAddress != null) {
                ipAddress = ipAddress.split(SEPARATOR)[0].trim();
            }
        } catch (Exception e) {
            log.error("解析请求IP失败", e);
            ipAddress = "";
        }
        return ipAddress == null ? "" : ipAddress;
    }

    /**
     * 获取本地IP地址
     *
     * @return 本地IP地址
     */
    public static String getLocalAddress() {
        if (inetUtils == null) {
            inetUtils = Application.getBean(InetUtils.class);
        }
        return inetUtils.findFirstNonLoopbackAddress().getHostAddress();
    }

    public static String getLocalIpV6Address() {
        if (inetIPv6Utils == null) {
            inetIPv6Utils = Application.getBean(InetIPv6Utils.class);
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
