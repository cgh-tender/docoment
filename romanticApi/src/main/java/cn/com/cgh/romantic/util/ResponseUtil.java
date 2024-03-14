package cn.com.cgh.romantic.util;

import com.alibaba.fastjson2.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * @author cgh
 */
public class ResponseUtil {
    public static Mono<Void> writeResponse(ServerHttpResponse response, Object data){
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(JSON.toJSONBytes(data))));
    }
    public static Mono<Void> writeResponse(ServerHttpResponse response,  byte[] data){
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(data)));
    }
}
