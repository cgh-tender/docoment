package cn.com.filter.token.Impl;

import cn.com.SpringContextUtil;
import cn.com.filter.token.Body.Impl.TokenUserNamePayload;
import cn.com.filter.token.Body.Impl.TokenUserPhonePayload;
import cn.com.filter.token.Body.TokenPayloadAbs;
import cn.com.filter.token.TokenBuilder;
import cn.com.filter.token.TokenService;
import cn.com.filter.token.TokenVerifyService;
import cn.com.utils.Redis.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.jsonwebtoken.*;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j
public class MySigningKey implements TokenService, TokenVerifyService, TokenBuilder {
    @Resource
    private RedisUtil redisUtil;
    private TokenPayloadAbs tokenPayloadAbs;
    private String TOKEN;
    public static SpringContextUtil springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);
    private final String typ = "JWT";
    private final long TOKEN_EXPIRE_MILLIS = 1000 * 60 * springContextUtil.getTokenExpireMinute();
    private final long SYS_EXPIRE_SECONDS = 60 * springContextUtil.getExpireMinute();
    private final Boolean USER_ONLINE = springContextUtil.getUserOnline();
    private SignatureAlgorithm SINHASH = SignatureAlgorithm.PS256;

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
        if (springContextUtil.getTokenPay().equals("userName")){
            return JSONObject.parseObject(JSONObject.toJSONString(claims), TokenUserNamePayload.class);
        }else if (springContextUtil.getTokenPay().equals("Phone")){
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
        builder.signWith(new MySigningKeyResolver().getKey2(String.valueOf(tokenPayloadAbs.getUuid())),SINHASH);
        return builder;
    }

    public JwtBuilder builder() {
        long l = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setHeader(getHeader())
                .setId(String.valueOf(tokenPayloadAbs.getUuid()))
                .setSubject(springContextUtil.getTokeSubject())
                .setIssuedAt(new Date(l))
                .setExpiration(new Date(l + TOKEN_EXPIRE_MILLIS))
                .addClaims(JSONObject.parseObject(JSONObject.toJSONString(tokenPayloadAbs), new TypeReference<Map<String, Object>>(){}));;
        builder.signWith(new MySigningKeyResolver().getKey2(String.valueOf(tokenPayloadAbs.getUuid())),SINHASH);
        return builder;
    }

    @Override
    public Boolean isOverdue() {
        try {
            Jwts.parser().setSigningKeyResolver(new MySigningKeyResolver()).parseClaimsJwt(TOKEN);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * redis 中存入的Key值
     * @return
     */
    private String getKey(){
        String key = "";
        if (USER_ONLINE){
            if (springContextUtil.getTokenPay().equals("userName")){
                TokenUserNamePayload tokenUserNamePayload = (TokenUserNamePayload) decodeToken();
                key = tokenUserNamePayload.getUserName();
            }else if (springContextUtil.getTokenPay().equals("Phone")){
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
            return Jwts.parser().setSigningKeyResolver(new MySigningKeyResolver()).parseClaimsJws(TOKEN).getBody();
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            return e.getClaims();
        }
    }

    private HashMap<String, Object> getHeader() {
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", springContextUtil.getAlg());
        header.put("typ", typ);
        return header;
    }

    class MySigningKeyResolver extends SigningKeyResolverAdapter {

        private byte[] decode(String secret) {
            return Base64.decodeBase64(secret);
        }

        public Key getKey(String id) {
            String key = springContextUtil.getTokenSalt() + id;
            byte[] decode = decode(key);
            return new SecretKeySpec(decode,0,decode.length,"AES");
        }

        @SneakyThrows
        public Key getKey2(String id) {
            String key = springContextUtil.getTokenSalt() + id;
            KeyGenerator keygen= KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom(key.getBytes()));
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            return new SecretKeySpec(raw, "AES");
        }

        @Override
        public Key resolveSigningKey(JwsHeader header, Claims claims) {
            return getKey2(claims.getId());
        }

    }
}
