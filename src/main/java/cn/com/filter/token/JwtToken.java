package cn.com.filter.token;

import cn.com.SpringContextUtil;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@Log4j
public class JwtToken implements Serializable,JwtSuper {
    private SpringContextUtil springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);
    private final String alg = springContextUtil.getAlg();
    private final String typ = "JWT";
    private final String toeknSalt = springContextUtil.getTokenSalt();
    //对设置的加密 密文 进行复杂化 处理 防止设置的长度不足
    private final String Subject = springContextUtil.getTokeSubject();

    public static final String ClaimName = "TokenPayloadAbs";
    private static final long serialVersionUID = SpringContextUtil.serialVersionUID;
    private final long TOKEN_EXPIRE_MILLIS = 1000 * 60 * springContextUtil.getTokenExpireMinute();

    @Override
    public <T extends TokenPayloadAbs> String createJwtToken(T payload) throws Exception {
        JwtBuilder builder = Jwts.builder();
        final String uuid = String.valueOf(payload.getUUID());
        long time = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        map.put(ClaimName, JSONObject.toJSONString(payload));
        builder.setHeader(getHeader());
        builder.setClaims(map); // 设置主体
        builder.setSubject(Subject); // 设置主题
        builder.setId(uuid); //设置 ID
        builder.setIssuedAt(new Date(time)); //设置签发时间
        builder.setExpiration(new Date(time + TOKEN_EXPIRE_MILLIS));// 设置过期时间
        //设置密钥 并返回 生成 Token
        if (alg.equals("SecretKey")) {
            return builder.signWith(getSecretKey()).compact();
        } else if (alg.equals("PairKey")) {
            return createJwtPairKeyToken(builder);
        } else if (alg.equals("MyKey")) {
            return createJwtMyKeyToken(builder);
        } else {
            throw new Exception("请传入正确的 Token 的加密方式");
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(toeknSalt.getBytes());
    }

    private String createJwtPairKeyToken(JwtBuilder builder) throws Exception {
        int keySize = 2048;
        SecureRandom secureRandom = new SecureRandom();

        // 为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(keySize, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        log.info(keyPair.getPublic());
        log.info(keyPair.getPrivate());
        builder.signWith(keyPair.getPrivate());
        return builder.compact();
    }

    private String createJwtMyKeyToken(JwtBuilder builder) throws Exception {
        return null;
    }

    public boolean isExpiration(String token){
        try {
           Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
           return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Claims getClaims(String token){
        try {
            return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public TokenPayloadAbs getTokenPayloadAbs(String token) throws Exception {
        Claims claims = getClaims(token);
        JSONObject jsonObject = JSONObject.parseObject((String) claims.get(ClaimName));
        String type = (String) jsonObject.get("type");
        return getTokenPayloadAbs(claims,type);
    }

    public TokenPayloadAbs getTokenPayloadAbs(Claims claims,String type) throws Exception {
        if (type.toUpperCase().equals("USERNAME")){
            return getTokenPayloadAbs(claims,TokenUserNamePayload.class);
        }else if(type.toUpperCase().equals("USERPHONE")){
            return getTokenPayloadAbs(claims,TokenUserPhonePayload.class);
        }else {
            throw new Exception(String.format("JwtType.class 不支持当前[%s]类型转换!", type));
        }
    }

    public <T extends TokenPayloadAbs> TokenPayloadAbs getTokenPayloadAbs(Claims claims, Class<T> clazz) throws Exception {
        String abs = (String) claims.get(ClaimName);
        try {
            return JSONObject.parseObject(abs, clazz);
        } catch (Exception e) {
            throw new Exception(String.format("JwtType.class 不支持当前[%s]类型转换!", clazz.getName()));
        }
    }

    public String getTokenUUID(String token) throws Exception {
        return String.valueOf(getTokenPayloadAbs(token).getUUID());
    }

    private HashMap<String, Object> getHeader() {
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", alg);
        header.put("typ", typ);
        return header;
    }
}

/**
 * <p> Jwt 自定义父类型
 * @author Haidar
 * @date 2020/1/13 09:58
 **/
interface JwtSuper{
    /**
     * <p> token 生成接口
     * @param payload 传入 Token 的主体内容
     * @return String Token
     * @author Haidar
     * @throws Exception 抛出异常信息
     * @date 2020/1/13 09:58
     **/
    <T extends TokenPayloadAbs> String createJwtToken(T payload) throws Exception;
}

/**
 * <p> USERNAME 用户名进行签名 PHONE 用户手机号进行签名
 * @author Haidar
 * @date 2020/1/13 09:57
 **/
enum JwtType{
    USERNAME("userName"),PHONE("userPhone");
    public String type;

    JwtType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
