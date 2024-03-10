package cn.com.cgh.romantic.config.aspect;

import cn.com.cgh.romantic.config.WebfluxAOPConfig;
import cn.com.cgh.romantic.config.aspect.annotation.RequestLock;
import cn.com.cgh.romantic.constant.RomanticConstant;
import cn.com.cgh.romantic.exception.RequestLockException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.BindException;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Method;

/**
 * @author cgh
 */
@Aspect
@Configuration
@Order(2)
public class RedisRequestLockAspect {
    private final RedisTemplate<String, Object> redisTemplateSO;

    @Autowired
    public RedisRequestLockAspect(RedisTemplate<String, Object> redisTemplateSO) {
        this.redisTemplateSO = redisTemplateSO;
    }

    @Around("execution(public * * (..)) && @annotation(cn.com.cgh.romantic.config.aspect.annotation.RequestLock)")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws BindException {
        ServerWebExchange serverWebExchange = WebfluxAOPConfig.RequestContextHolder.get();
        if (serverWebExchange != null) {
            ServerHttpRequest request = serverWebExchange.getRequest();
            String first = request.getHeaders().getFirst(RomanticConstant.X_REAL_IP);
            System.out.println(first);
            // 现在你可以使用request对象了
        }
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RequestLock requestLock = method.getAnnotation(RequestLock.class);
        if (StringUtils.isEmpty(requestLock.prefix())) {
            throw new RequestLockException("重复提交前缀不能为空");
        }
        //获取自定义key
        final String lockKey = RequestKeyGenerator.getLockKey(joinPoint);
        // 使用RedisCallback接口执行set命令，设置锁键；设置额外选项：过期时间和SET_IF_ABSENT选项
        final Boolean success = redisTemplateSO.execute(
                (RedisCallback<Boolean>) connection -> connection.stringCommands().set(lockKey.getBytes(), new byte[0],
                        Expiration.from(requestLock.expire(), requestLock.timeUnit()),
                        RedisStringCommands.SetOption.SET_IF_ABSENT));
        if (Boolean.FALSE.equals(success)) {
            throw new RequestLockException("您的操作太快了,请稍后重试");
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RequestLockException("系统异常");
        }
    }
}
