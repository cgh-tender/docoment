package cn.com.filter.token.Impl;

import cn.com.SpringContextUtil;
import cn.com.filter.token.Body.Impl.TokenUserNamePayload;
import cn.com.filter.token.Body.Impl.TokenUserPhonePayload;
import cn.com.filter.token.Body.TokenPayloadAbs;
import cn.com.filter.token.TokenBuilder;
import cn.com.filter.token.TokenService;
import cn.com.filter.token.TokenVerifyService;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.jsonwebtoken.*;
import io.jsonwebtoken.lang.Classes;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.security.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j
public class MyPairKey implements TokenService, TokenVerifyService, TokenBuilder {
    private TokenPayloadAbs tokenPayloadAbs;
    private String TOKEN;
    public static SpringContextUtil springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);
    private final String typ = "JWT";
    private final long TOKEN_EXPIRE_MILLIS = 1000 * 60 * springContextUtil.getTokenExpireMinute();
    private final int KEYSIZE = 2048;
    private SignatureAlgorithm SINHASH = SignatureAlgorithm.ES384; // HS384,HS256,HS512,RS256,PS256,RS384,PS384,RS512,PS512,ES256,ES384,ES512

    @Override
    public String getType() {
        return "2";
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
        builder.signWith(getPrivateKey());
        return builder;
    }

    private JwtBuilder builder() {
        long l = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setHeader(getHeader())
                .setId(String.valueOf(tokenPayloadAbs.getUuid()))
                .setSubject(springContextUtil.getTokeSubject())
                .setIssuedAt(new Date(l))
                .setExpiration(new Date(l + TOKEN_EXPIRE_MILLIS))
                .addClaims(JSONObject.parseObject(JSONObject.toJSONString(tokenPayloadAbs), new TypeReference<Map<String, Object>>(){}));;
        builder.signWith(getPrivateKey());
        return builder;
    }

    @Override
    public Boolean isOverdue() {
        try {
            Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(TOKEN);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public KeyPair getKeyPair(){
        KeyPair keyPair;
        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator keyPairGenerator = null;
            try {
                keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            keyPairGenerator.initialize(KEYSIZE, secureRandom);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            keyPair = Keys.keyPairFor(SINHASH);
        }
        saveKeyPair(keyPair);
        return keyPair;
    }
    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public PrivateKey getPrivateKey(){
        return getKeyPair().getPrivate();
    }

    private KeyPair keyPair;
    private void saveKeyPair(KeyPair keyPair){
        this.keyPair = keyPair;
    }

    private HashMap<String, Object> getHeader(){
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", springContextUtil.getAlg());
        header.put("typ", typ);
        return header;
    }

    @Override
    public String getToken() {
        return builder().compact();
    }

    @Override
    public String reToken() {
        return builder(getClaims()).compact();
    }

    @Override
    public Claims getClaims() {
        try {
            return Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(TOKEN).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
