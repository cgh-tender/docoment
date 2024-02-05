package cn.com.cgh.romantic.util;

import com.alibaba.fastjson2.JSON;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

public class ResponseUtil {
    public static Mono<Void> writeResponse(ServerHttpResponse response, Object data){
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JSON.toJSONBytes(data))));
    }
    public static Mono<Void> writeResponse(ServerHttpResponse response,  byte[] data){
        return response.writeWith(Mono.just(response.bufferFactory().wrap(data)));
    }
}
