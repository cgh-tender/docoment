package cn.com.cgh.auth.config;

import cn.com.cgh.romantic.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author cgh
 */
@Component
@Slf4j
public class TokenServerSecurityContextRepository implements ServerSecurityContextRepository {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ExecutorService blockingExecutor;

    public TokenServerSecurityContextRepository(ExecutorService blockingExecutor) {
        this.blockingExecutor = blockingExecutor;
    }
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        log.info("TokenServerSecurityContextRepository save ");
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            try {
                Long userIdFromToken = jwtTokenUtil.getUserIdFromToken(token);
                String userNameFromToken = jwtTokenUtil.getUserNameFromToken(token);
                SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("admin"));
                Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
                emptyContext.setAuthentication(authentication);
                return Mono.just(emptyContext);
            } catch (Exception e) {
                return Mono.empty();
            }
        }else if (request.getURI().getPath().equals("/login")){
            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            // 创建一个AtomicReference来存储请求体内容
            AtomicReference<String> requestBodyRef = new AtomicReference<>();
            ServerHttpRequest originalRequest = exchange.getRequest();
            // 创建一个新的请求对象，将请求体转换为Mono<String>
            Mono<SecurityContext> securityContextMono = DataBufferUtils.join(originalRequest.getBody())
                    .map(dataBuffer -> {
                        // 读取请求体并转换为字符串
                        String body = decodeDataBufferToString(dataBuffer);
                        log.info(body);
                        // 清理数据缓冲区
                        DataBufferUtils.release(dataBuffer);
                        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getQueryParams().getFirst("username"),
                                passwordEncoder.encode(request.getQueryParams().getFirst("password"))
                                , null);
                        emptyContext.setAuthentication(authentication);
                        return emptyContext;
                    });
            return securityContextMono.switchIfEmpty(Mono.defer(() -> {
                // 如果请求体为空，那么就返回一个空的SecurityContext
                return Mono.just(emptyContext);
            }));
        }
        return Mono.empty();
    }
    private String decodeDataBufferToString(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        DataBufferUtils.release(dataBuffer);
        return new String(bytes, StandardCharsets.UTF_8);
    }
    private ServerHttpRequest createNewRequestWithBody(ServerHttpRequest originalRequest, String body) {
        // 注意：通常情况下，我们不会在全局过滤器中创建新的带有请求体的请求，因为这可能会破坏框架内部的行为。
        // 这里只是为了演示如何读取和存储请求体而做的模拟操作。

        // 在实际应用中，你可以在不修改原始请求的情况下，在内存中保留请求体的内容以便后续处理

        return originalRequest;
    }


}
