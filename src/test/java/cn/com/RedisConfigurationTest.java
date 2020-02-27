package cn.com;

import cn.com.utils.Redis.RedisUtil;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j
public class RedisConfigurationTest {
    @Resource
    private RedisUtil redisUtil;

    @Test
    public void test(){
        redisUtil.set("111","aaa",1000);
        log.info(redisUtil.hasKey("111"));
    }

}