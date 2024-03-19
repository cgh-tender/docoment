package cn.com.cgh.romantic.util;

import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.com.cgh.romantic.constant.AuthConstant.JWT_TOKEN_PREFIX;
import static cn.com.cgh.romantic.constant.RomanticConstant.JWT_TOKEN_HEADER;
import static cn.com.cgh.romantic.util.KeyConstant.CONTENT_INSTANCE;

/**
 * @author cgh
 */
@AllArgsConstructor
@Slf4j
public class JwtTokenUtil {
    // jwt:userId:username:id
    public static final String JWT_CACHE_KEY = "jwt:{0}:{1}";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    private static final String EXPIRE_IN = "expire_in";

    private final RedisTemplate<String, Object> redisTemplateSO;
    private final IdWork idWork;

    public static final RSA RSA_RSA = new RSA(CONTENT_INSTANCE, KeyConstant.PRIVATE_KEY, KeyConstant.PUBLIC_KEY);

    public String getToken(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        // 从请求头获取token
        String token = headers.getFirst(JWT_TOKEN_HEADER);
        if (StringUtils.isBlank(token)) {
            // 请求头无token则从url获取token
            token = request.getQueryParams().getFirst(JWT_TOKEN_HEADER);
        }
        if (StringUtils.isBlank(token)) {
            // 请求头和url都没有token则从cookies获取
            HttpCookie cookie = request.getCookies().getFirst(JWT_TOKEN_HEADER);
            if (cookie != null) {
                token = cookie.getValue();
            }
        }
        return token(token);
    }

    /**
     * 生成 token 令牌主方法
     *
     * @param userId 用户Id或用户名
     * @return 令token牌
     */
    public Map<String, Object> generateTokenAndRefreshToken(Long userId, String username, Map<String, String> payload) {
        //生成令牌及刷新令牌
        Map<String, Object> tokenMap = buildToken(userId, username, payload);
        //redis缓存结果
        cacheToken(username, tokenMap);
        return tokenMap;
    }

    //将token缓存进redis
    private void cacheToken(String username, Map<String, Object> tokenMap) {
        String key = MessageFormat.format(JWT_CACHE_KEY, username, tokenMap.get(JWTPayload.JWT_ID));
        redisTemplateSO.opsForHash().put(key, ACCESS_TOKEN, tokenMap.get(ACCESS_TOKEN));
        redisTemplateSO.opsForHash().put(key, REFRESH_TOKEN, tokenMap.get(REFRESH_TOKEN));
        redisTemplateSO.opsForHash().put(key, JWTPayload.JWT_ID, username);
        flushTimeout(key);
    }

    public void flushTimeout(String key) {
        if (key.startsWith("Bearer ")) {
            String token = token(key);
            JWTPayload payload = new JWT(token(token)).getPayload();
            key = MessageFormat.format(JWT_CACHE_KEY, payload.getClaim(JWTPayload.SUBJECT), payload.getClaim(JWTPayload.JWT_ID));
        }
        redisTemplateSO.expire(key, 10, TimeUnit.MINUTES);
    }

    public Boolean exists(String key) {
        return redisTemplateSO.hasKey(key);
    }

    //生成令牌
    private Map<String, Object> buildToken(Long userId, String username, Map<String, String> payload) {
        //生成token令牌
        String accessToken = generateToken(userId, username, payload);
        //生成刷新令牌
        String refreshToken = generateRefreshToken(userId, username, payload);
        //存储两个令牌及过期时间，返回结果
        Map<String, Object> tokenMap = new HashMap<>(4);
        tokenMap.put(ACCESS_TOKEN, accessToken);
        tokenMap.put(REFRESH_TOKEN, refreshToken);
        tokenMap.put(JWTPayload.JWT_ID, payload.get(JWTPayload.JWT_ID));
        tokenMap.put(EXPIRE_IN, 10);
        return tokenMap;
    }

    /**
     * 生成 token 令牌 及 refresh token 令牌
     *
     * @param payloads 令牌中携带的附加信息
     * @return 令牌
     */
    public String generateToken(Long userId, String username,
                                Map<String, String> payloads) {
        Map<String, Object> claims = buildClaims(userId, username, payloads);
        return generateToken(claims);
    }

    public String generateRefreshToken(Long userId, String username, Map<String, String> payloads) {
        Map<String, Object> claims = buildClaims(userId, username, payloads);
        return generateRefreshToken(claims);
    }

    //构建map存储令牌需携带的信息
    private Map<String, Object> buildClaims(Long userId, String username, Map<String, String> payloads) {
        int payloadSizes = payloads == null ? 0 : payloads.size();
        Map<String, Object> payload = new HashMap<>(payloadSizes + 2);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime localDateTime = DateUtil.addSecond(now, 10);
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, localDateTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put(JWTPayload.SUBJECT, username);
        payload.put(JWTPayload.AUDIENCE, userId);
        if (payloadSizes > 0) {
            payload.putAll(payloads);
        }
        return payload;
    }


    /**
     * TODO 有bug
     * 刷新令牌并生成新令牌
     * 并将新结果缓存进redis
     */
    public Map<String, Object> refreshTokenAndGenerateToken(Long userId, String username) {
        Map<String, Object> tokenMap = buildToken(userId, username, new HashMap<>());
//        redisTemplateSO.delete(JWT_CACHE_KEY + tokenMap.get(JWTPayload.JWT_ID));
//        cacheToken(username, tokenMap);
        return tokenMap;
    }

    //缓存中删除token
    public boolean removeToken(String username, String id) {
        return removeToken(username, id,1);
    }
    public boolean removeToken(String username, String id,long timeout) {
        return Boolean.TRUE.equals(redisTemplateSO.expire(MessageFormat.format(JWT_CACHE_KEY, username, id), timeout, TimeUnit.MILLISECONDS));
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUserNameFromToken(String token) {
        return String.valueOf(new JWT(token(token)).getPayload().getClaim(JWTPayload.SUBJECT));
    }

    public String getIdFromToken(String token) {
        return String.valueOf(new JWT(token(token)).getPayload().getClaim(JWTPayload.JWT_ID));
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(String.valueOf(new JWT(token(token)).getPayload().getClaim(JWTPayload.AUDIENCE)));
    }

    public String token(String token) {
        return token == null ? null : token.replace(JWT_TOKEN_PREFIX, "");
    }

    /**
     * 判断令牌是否不存在 redis 中
     *
     * @param token 刷新令牌
     * @return true=不存在，false=存在
     */
    public Boolean isRefreshTokenNotExistCache(String token) {
        token = token(token);
        String id = getIdFromToken(token);
        String username = getUserNameFromToken(token);
        String refreshToken = (String) redisTemplateSO.opsForHash().get(MessageFormat.format(JWT_CACHE_KEY, username, id), REFRESH_TOKEN);
        return refreshToken == null || !refreshToken.equals(token);
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
            claims.set(JWTPayload.ISSUED_AT, LocalDateTime.now());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims) {
        return JWTUtil.createToken(claims, JWTSignerUtil.createSigner(CONTENT_INSTANCE, RSA_RSA.getPrivateKey()));
    }

    /**
     * 生成刷新令牌 refreshToken，有效期是令牌的 2 倍
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateRefreshToken(Map<String, Object> claims) {
        LocalDateTime dateTime = (LocalDateTime) claims.get(JWTPayload.EXPIRES_AT);
        dateTime = DateUtil.addSecond(dateTime, 10);
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
        return new JWT(token).getPayload().getClaimsJson();
    }
}
