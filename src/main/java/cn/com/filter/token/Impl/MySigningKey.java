package cn.com.filter.token.Impl;

import cn.com.SpringContextUtil;
import cn.com.filter.token.Body.Impl.TokenUserNamePayload;
import cn.com.filter.token.Body.Impl.TokenUserPhonePayload;
import cn.com.filter.token.Body.TokenPayloadAbs;
import cn.com.filter.token.TokenBuilder;
import cn.com.filter.token.TokenConfig;
import cn.com.filter.token.TokenService;
import cn.com.filter.token.TokenVerifyService;
import cn.com.filter.token.Utils.TokenRedisUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j
public class MySigningKey implements TokenService, TokenVerifyService, TokenBuilder {
    @Resource
    private TokenRedisUtil redisUtil;
    private TokenPayloadAbs tokenPayloadAbs;
    private String TOKEN;
    public static TokenConfig tokenCfg = SpringContextUtil.getBean(TokenConfig.class);
    private final String typ = "JWT";
    private final long TOKEN_EXPIRE_MILLIS = 1000 * 60 * tokenCfg.getTokenExpireMinute();
    private final long USER_EXPIRE_MILLIS = 60 * tokenCfg.getLoginLockMinute();
    private final long SYS_EXPIRE_SECONDS = 60 * tokenCfg.getExpireMinute();
    private final Boolean USER_ONLINE = tokenCfg.getUserOnline();
    private final int LOGIN_NUM = tokenCfg.getLoginNum();

    @Override
    public String getType() {
        return "3";
    }

    @Override
    public TokenService builder(TokenPayloadAbs t){
        this.tokenPayloadAbs = t;
        return this;
    }

    @Override
    public TokenVerifyService builder(String token){
        this.TOKEN = token;
        return this;
    }

    @Override
    public TokenPayloadAbs decodeToken() {
        Claims claims = getClaims();
        if (tokenCfg.getTokenPay().equals("userName")){
            return JSONObject.parseObject(JSONObject.toJSONString(claims), TokenUserNamePayload.class);
        }else if (tokenCfg.getTokenPay().equals("Phone")){
            return JSONObject.parseObject(JSONObject.toJSONString(claims), TokenUserPhonePayload.class);
        }
        log.error("请配置 shiro.token.tokenPay 参数");
        return null;
    }

    private JwtBuilder builder(Claims claims){
        long l = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setHeader(getHeader())
                .addClaims(claims)
                .setIssuedAt(new Date(l))
                .setExpiration(new Date(l + TOKEN_EXPIRE_MILLIS));
        builder.signWith(new MySigningKeyResolver().getKey(String.valueOf(claims.getId())));
        return builder;
    }

    public JwtBuilder builder() {
        long l = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setHeader(getHeader())
                .setId(String.valueOf(tokenPayloadAbs.getUuid()))
                .setSubject(tokenCfg.getTokeSubject())
                .setIssuedAt(new Date(l))
                .setExpiration(new Date(l + TOKEN_EXPIRE_MILLIS))
                .addClaims(JSONObject.parseObject(JSONObject.toJSONString(tokenPayloadAbs), new TypeReference<Map<String, Object>>(){}));;
        builder.signWith(new MySigningKeyResolver().getKey(String.valueOf(tokenPayloadAbs.getUuid())));
        return builder;
    }

    @Override
    public Boolean isOverdue() {
        try {
            Jwts.parser()
                    .requireSubject(tokenCfg.getTokeSubject())
                    .setSigningKeyResolver(new MySigningKeyResolver())
                    .parseClaimsJwt(TOKEN);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private String getUserKey(){
        String key = "";
        if (tokenCfg.getTokenPay().equals("userName")){
            TokenUserNamePayload tokenUserNamePayload = (TokenUserNamePayload) decodeToken();
            key = tokenUserNamePayload.getUserName();
        }else if (tokenCfg.getTokenPay().equals("Phone")){
            TokenUserPhonePayload tokenUserNamePayload = (TokenUserPhonePayload) decodeToken();
            key = tokenUserNamePayload.getPhone();
        }
        return key;
    }

    @Override
    public int remainLoginNum() {
        String userKey = getUserKey();
        int num = -1;
        if (redisUtil.hasKey(userKey)){
            num = (int)redisUtil.get(userKey) - 1;
        }else {
            num = LOGIN_NUM - 1;
        }
        //锁定用户
        if (num >= 0){
            if (num == 0){
                redisUtil.set(userKey,num,USER_EXPIRE_MILLIS);
            }else {
                redisUtil.set(userKey,num);
            }
        }else {
            return -1;
        }
        return num;
    }

    @Override
    public long remainLoginTime() {
        String userKey = getUserKey();
        if (redisUtil.hasKey(userKey)){
            return redisUtil.getExpire(getUserKey());
        }else {
            return -1;
        }

    }

    /**
     * redis 中存入的Key值
     * @return
     */
    private String getKey(){
        String key = "";
        if (USER_ONLINE){
            if (tokenCfg.getTokenPay().equals("userName")){
                TokenUserNamePayload tokenUserNamePayload = (TokenUserNamePayload) decodeToken();
                key = tokenUserNamePayload.getUserName();
            }else if (tokenCfg.getTokenPay().equals("Phone")){
                TokenUserPhonePayload tokenUserNamePayload = (TokenUserPhonePayload) decodeToken();
                key = tokenUserNamePayload.getPhone();
            }
        }else {
            Claims claims = getClaims();
            key = claims.getId();
        }
        return key;
    }

    @Override
    public Boolean SysIsOverdue() {
        return !redisUtil.hasKey(getKey());
    }

    @Override
    public Boolean saveToken() {
        try {
            redisUtil.set(getKey(),TOKEN,SYS_EXPIRE_SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean upTokenTime() {
        try {
            redisUtil.expire(getKey(),SYS_EXPIRE_SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String getToken() {
        return builder().compact();
    }

    @Override
    public String reToken() {
        if (!redisUtil.get(getKey()).equals(TOKEN)){
            return (String) redisUtil.get(getKey());
        }
        return builder(getClaims()).compact();
    }

    @Override
    public Claims getClaims() {
        try {
            return Jwts.parser()
                    .requireSubject(tokenCfg.getTokeSubject())
                    .setSigningKeyResolver(new MySigningKeyResolver())
                    .parseClaimsJws(TOKEN)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    private HashMap<String, Object> getHeader() {
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", tokenCfg.getAlg());
        header.put("typ", typ);
        return header;
    }

    class MySigningKeyResolver extends SigningKeyResolverAdapter {
        @SneakyThrows
        public Key getKey(String id) {
            String i = tokenCfg.getTokenSalt() + id;
            KeyGenerator keygen= KeyGenerator.getInstance("AES");
            keygen.init(256, new SecureRandom(i.getBytes()));
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            SecretKey secretKey = Keys.hmacShaKeyFor(raw);
            redisUtil.set(i,secretKey,SYS_EXPIRE_SECONDS);
            return secretKey;
        }

        @SneakyThrows
        public Key getKey2(String id) {
            String i = tokenCfg.getTokenSalt() + id;
            return (SecretKey) redisUtil.get(i);
        }

        @Override
        public Key resolveSigningKey(JwsHeader header, Claims claims) {
            return getKey2(claims.getId());
        }

    }
    @Override
    public void clear() {
        String i = tokenCfg.getTokenSalt() + getClaims().getId();
        redisUtil.del(getKey(),i);
    }
}
