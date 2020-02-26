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
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Log4j
public class MySigningKey implements TokenService, TokenVerifyService, TokenBuilder {
    private TokenPayloadAbs tokenPayloadAbs;
    private String TOKEN;
    public static SpringContextUtil springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);
    private final String typ = "JWT";
    private final long TOKEN_EXPIRE_MILLIS = 1000 * 60 * springContextUtil.getTokenExpireMinute();
    private SignatureAlgorithm SINHASH = SignatureAlgorithm.HS512; // HS384,HS256,HS512

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
        String key = springContextUtil.getTokenSalt() + claims.getId();
        builder.signWith(new SecretKeySpec(key.getBytes(), SINHASH.getJcaName()));
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
        String key = springContextUtil.getTokenSalt() + tokenPayloadAbs.getUuid();
        builder.signWith(new SecretKeySpec(key.getBytes(), SINHASH.getJcaName()));
        return builder;
    }

    @Override
    public Boolean isOverdue() {
        try {
            Jwts.parser().setSigningKeyResolver(new MySigningKeyResolver()).parseClaimsJws(TOKEN);
            return false;
        } catch (Exception e) {
            return true;
        }
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
            return Jwts.parser().setSigningKeyResolver(new MySigningKeyResolver()).parseClaimsJws(TOKEN).getBody();
        } catch (ExpiredJwtException e) {
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
        @Override
        public Key resolveSigningKey(JwsHeader header, Claims claims) {
            claims.getId();
            String key = springContextUtil.getTokenSalt() + claims.getId();
            return Keys.hmacShaKeyFor(key.getBytes());
//            return super.resolveSigningKey(header, claims);
        }
    }
}
