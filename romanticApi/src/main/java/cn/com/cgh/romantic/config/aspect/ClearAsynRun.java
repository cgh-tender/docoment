package cn.com.cgh.romantic.config.aspect;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author cgh
 */
@Service
@AllArgsConstructor
public class ClearAsynRun {

    private final RedisTemplate<String, Object> redisTemplateSO;
    /**
     * 异步清除Redis缓存。
     * 该方法将指定的键值集合从Redis缓存中异步删除。
     *
     * @param keys 要删除的Redis键的集合。
     * 注意：该方法不返回任何结果，即没有返回值。
     */
    @Async
    public void clearRedisCache(Collection<String> keys){
        try {
            // 暂停500毫秒，旨在确保在删除操作之前，其他操作有足够时间完成对这些键的更新
            Thread.sleep(500);
            // 使用Redis模板删除指定的键
            redisTemplateSO.delete(keys);
        } catch (InterruptedException e) {
            // 捕获中断异常，但不处理，允许方法继续执行
        }
    }
}
