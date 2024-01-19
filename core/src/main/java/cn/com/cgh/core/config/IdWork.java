package cn.com.cgh.core.config;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class IdWork {
    private static final long BEGIN_TIMESTAMP = 1669503600L;

    private final RedisTemplate<String,Object> redisTemplateSO;
    private static final int COUNT_BITS = 32;
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy:MM:dd");

    private static final String DEFAULT = "default";

    public long nextId() {
        return nextId(DEFAULT);
    }
    public long nextId(String kyePrefix) {
        //1：生成时间戳 = 当前时间戳-开始时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timeStamp = nowSecond - BEGIN_TIMESTAMP;
        //2：生成序列号.使用sexNx.其中加上当前年月日
        //获取当前时间的你那月日
        //开始32位序列号
        long no = redisTemplateSO.opsForValue().increment("icr:%s:%s".formatted(kyePrefix, now.format(dateTimeFormatter)));
        //3:拼接返回
        return timeStamp << COUNT_BITS | no;
    }
    public List<Long> nextId(String kyePrefix, long size) {
        //1：生成时间戳 = 当前时间戳-开始时间戳
        LocalDateTime now = LocalDateTime.now();
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        long timeStamp = nowSecond - BEGIN_TIMESTAMP;
        //2：生成序列号.使用sexNx.其中加上当前年月日
        //获取当前时间的你那月日
        //开始32位序列号
        Long increment = redisTemplateSO.opsForValue().increment("icr:%s:%s".formatted(kyePrefix, now.format(dateTimeFormatter)), size);
        List<Long> nos = new ArrayList<>((int) size);
        for (long i = 0; i < size; i++) {
            nos.add(timeStamp << COUNT_BITS | (increment - size + i + 1));
        }
        //3:拼接返回
        return nos;
    }
}
