package cn.com.cgh.romantic.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
public class JwtTokenUtil {


    private static final String JWT_CACHE_KEY = "jwt:userId:";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    private static final String EXPIRE_IN = "expire_in";

//    private final RedisTemplate<String, Object> redisTemplateSO;

    private static final RSA RSA_RSA = new RSA(AsymmetricAlgorithm.RSA.getValue(),KeyConstant.PUBLIC_KEY,KeyConstant.PRIVATE_KEY);

    /**
     * 生成 token 令牌主方法
     * @param userId 用户Id或用户名
     * @return 令token牌
     */
    public Map<String, Object> generateTokenAndRefreshToken(Integer userId, String username) {
        //生成令牌及刷新令牌
        Map<String, Object> tokenMap = buildToken(userId, username);
        //redis缓存结果
        cacheToken(userId, tokenMap);
        return tokenMap;
    }

    //将token缓存进redis
    private void cacheToken(Integer userId, Map<String, Object> tokenMap) {
//        redisTemplateSO.opsForHash().put(JWT_CACHE_KEY + userId, ACCESS_TOKEN, tokenMap.get(ACCESS_TOKEN));
//        redisTemplateSO.opsForHash().put(JWT_CACHE_KEY + userId, REFRESH_TOKEN, tokenMap.get(REFRESH_TOKEN));
//        redisTemplateSO.expire(String.valueOf(userId), 10, TimeUnit.MINUTES);
    }
    //生成令牌
    private Map<String, Object> buildToken(Integer userId, String username) {
        //生成token令牌
        String accessToken = generateToken(userId, username, null);
        //生成刷新令牌
        String refreshToken = generateRefreshToken(userId, username, null);
        //存储两个令牌及过期时间，返回结果
        Map<String, Object> tokenMap = new HashMap<>(2);
        tokenMap.put(ACCESS_TOKEN, accessToken);
        tokenMap.put(REFRESH_TOKEN, refreshToken);
        tokenMap.put(EXPIRE_IN, 10);
        return tokenMap;
    }
    /**
     * 生成 token 令牌 及 refresh token 令牌
     * @param payloads 令牌中携带的附加信息
     * @return 令牌
     */
    public String generateToken(Integer userId, String username,
                                Map<String,String> payloads) {
        Map<String, Object> claims = buildClaims(userId, username, payloads);
        return generateToken(claims);
    }
    public String generateRefreshToken(Integer userId, String username, Map<String,String> payloads) {
        Map<String, Object> claims = buildClaims(userId, username, payloads);
        return generateRefreshToken(claims);
    }
    //构建map存储令牌需携带的信息
    private Map<String, Object> buildClaims(Integer userId, String username, Map<String, String> payloads) {
        int payloadSizes = payloads == null? 0 : payloads.size();
        Map<String, Object> payload = new HashMap<>(payloadSizes + 2);
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.MINUTE, 10);
        log.info(newTime.toTimeStr());
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put(JWTPayload.SUBJECT, username);
        payload.put(JWTPayload.JWT_ID, userId);
        if(payloadSizes > 0){
            payload.putAll(payloads);
        }
        return payload;
    }


    /**
     * 刷新令牌并生成新令牌
     * 并将新结果缓存进redis
     */
    public Map<String, Object> refreshTokenAndGenerateToken(Integer userId, String username) {
        Map<String, Object> tokenMap = buildToken(userId, username);
//        redisTemplateSO.delete(JWT_CACHE_KEY + userId);
        cacheToken(userId, tokenMap);
        return tokenMap;
    }

    //缓存中删除token
    public boolean removeToken(String userId) {
//        return Boolean.TRUE.equals(redisTemplateSO.delete(JWT_CACHE_KEY + userId));
        return false;
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUserNameFromToken(String token) {
        JWT jwt = new JWT(token);
        return String.valueOf(jwt.getPayload().getClaim(JWTPayload.SUBJECT));
    }
    public String getUserIdFromToken(String token) {
        JWT jwt = new JWT(token);
        return String.valueOf(jwt.getPayload().getClaim(JWTPayload.SUBJECT));
    }


    /**
     * 判断令牌是否不存在 redis 中
     *
     * @param token 刷新令牌
     * @return true=不存在，false=存在
     */
    public Boolean isRefreshTokenNotExistCache(String token) {
//        String userId = getUserIdFromToken(token);
//        String refreshToken = (String)redisTemplateSO.opsForHash().get(JWT_CACHE_KEY + userId, REFRESH_TOKEN);
//        return refreshToken == null || !refreshToken.equals(token);
        return false;
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            JSONObject claims = getClaimsFromToken(token);
            claims.put(JWTPayload.ISSUED_AT, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 生成令牌
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims) {
        return JWTUtil.createToken(claims, JWTSignerUtil.createSigner("RSA", RSA_RSA.getPublicKey()));
    }
    /**
     * 生成刷新令牌 refreshToken，有效期是令牌的 2 倍
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateRefreshToken(Map<String, Object> claims) {
        DateTime dateTime = (DateTime) claims.get(JWTPayload.EXPIRES_AT);
        dateTime.offsetNew(DateField.MINUTE, 10);
        log.info(dateTime.toTimeStr());
        claims.put(JWTPayload.EXPIRES_AT, dateTime);
        return generateToken(claims);
    }

    /**
     * 从令牌中获取数据声明,验证 JWT 签名
     *
     * @param token 令牌
     * @return 数据声明
     */
    private JSONObject getClaimsFromToken(String token) {
        JSONObject claimsJson = new JWT(token).getPayload().getClaimsJson();
        return claimsJson;
    }

    public static void main(String[] args) {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        Map<String, Object> test = jwtTokenUtil.generateTokenAndRefreshToken(1, "test");
        System.out.println(test);
    }

}
