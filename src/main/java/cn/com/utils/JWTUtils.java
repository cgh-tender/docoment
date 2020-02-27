package cn.com.utils;

import cn.com.SpringContextUtil;
import cn.com.filter.token.Body.TokenPayloadAbs;
import cn.com.filter.token.TokenServiceFactory;
import javax.annotation.Resource;

public class JWTUtils {
    @Resource
    private static TokenServiceFactory tokenServiceFactory;
//    @Resource
//    private RedisUtil redisUtil;
//    @Autowired
//    private JedisCluster jedisCluster;

    public static SpringContextUtil springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);

    private JWTUtils(){}

    /**
     * 获取Token
     * @param tokenPayloadAbs
     * @return
     */
    public static String getToken(TokenPayloadAbs tokenPayloadAbs){
        return tokenServiceFactory.getInstance().builder(tokenPayloadAbs).getToken();
    }

    /**
     * Token是否过期
     * @param token
     * @return (是)true,(否)false
     */
    public static boolean isOverdue(String token){
        return tokenServiceFactory.getInstance().builder(token).isOverdue();
    }

    /**
     * 系统是否过期
     * @param token
     * @return (是)true,(否)false
     */
    public static boolean SysIsOverdue(String token){
        return tokenServiceFactory.getInstance().builder(token).SysIsOverdue();
    }

    /**
     * 重新获取Token
     * @param token
     * @return
     */
    public static String reToken(String token){
        return tokenServiceFactory.getInstance().builder(token).reToken();
    }



}
