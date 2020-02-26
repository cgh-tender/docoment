package cn.com.utils;

import cn.com.SpringContextUtil;
import cn.com.filter.token.TokenServiceFactory;
import cn.com.utils.Redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

public class JWTUtils {
    @Resource
    private TokenServiceFactory tokenServiceFactory;
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private JedisCluster jedisCluster;

    public static SpringContextUtil springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);

    private JWTUtils(){}

    public String gettoken(){
        return null;
//        return tokenServiceFactory.getInstance().builder(new TokenUserNamePayload("admin", Object)).getToken();
    }

}
