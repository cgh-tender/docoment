package cn.com.filter.token.Key;

import cn.com.SpringContextUtil;
import cn.com.filter.token.Body.TokenPayloadAbs;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

public class MyCustom implements KeyFactory {
    private TokenPayloadAbs tokenPayloadAbs;

    public static SpringContextUtil springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);

    private final String alg = springContextUtil.getAlg();
    private final String typ = "JWT";
    public static final String toeknSalt = springContextUtil.getTokenSalt();
    //对设置的加密 密文 进行复杂化 处理 防止设置的长度不足
    private final String Subject = springContextUtil.getTokeSubject();

    public static final String ClaimName = "TokenPayloadAbs";
    private static final long serialVersionUID = SpringContextUtil.serialVersionUID;
    private final long TOKEN_EXPIRE_MILLIS = 1000 * 60 * springContextUtil.getTokenExpireMinute();

    private MyCustom(){}

    public MyCustom(TokenPayloadAbs t){
        this.tokenPayloadAbs = t;
    }

    @Override
    public TokenPayloadAbs decodeToken(String token) {
        return null;
    }

    @Override
    public JwtBuilder builder() {
        long l = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setHeader(getHeader())
                .setId(String.valueOf(tokenPayloadAbs.getUUID()))
                .setSubject(Subject)
                .setIssuedAt(new Date(l))
                .setExpiration(new Date(l + TOKEN_EXPIRE_MILLIS));
        tokenPayloadAbs.inItJwtBuilder(tokenPayloadAbs, builder);
        builder.signWith(getPublicKey(toeknSalt));
        return builder;
    }

    @Override
    public Key getPublicKey(String key) {
        return null;
    }

    @Override
    public Key getPrivate(String key) {
        return null;
    }

    private HashMap<String, Object> getHeader() {
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", alg);
        header.put("typ", typ);
        return header;
    }
}
