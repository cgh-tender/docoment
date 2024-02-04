package cn.com.cgh.core.util;

import com.alibaba.cloud.nacos.util.InetIPv6Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class RequestUtil {
    protected static InetUtils inetUtils;
    protected static InetIPv6Utils inetIPv6Utils;
    protected static final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    public ByteBuffer toSingleByteBuffer(DataBuffer dataBuffer) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(dataBuffer.readableByteCount());
        dataBuffer.read(byteBuffer.array());
        byteBuffer.flip();
        return byteBuffer;
    }

    public static AtomicReference<String> getBody(ServerWebExchange exchange) {
        AtomicReference<String> reference = new AtomicReference<>("");
        String first = exchange.getRequest().getHeaders().getFirst("Content-Type");
        System.out.println(first);
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);
        serverRequest.bodyToMono(String.class).subscribe(reference::set);
        return reference;
    }

    public static Mono<String> getIpAddr(ServerWebExchange exchange) {
        return Mono.just(exchange)
                .map(ServerWebExchange::getRequest)
                .map(ServerHttpRequest::getRemoteAddress)
                .flatMap(remoteAddress -> Mono.just(remoteAddress.getAddress().getHostAddress()));
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
