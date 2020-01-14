package cn.com.filter.token;

import cn.com.SpringContextUtil;
import io.jsonwebtoken.JwtBuilder;

import java.io.Serializable;

public abstract class JWTFactory<T extends TokenPayloadAbs> implements Serializable {

    private static final long serialVersionUID = SpringContextUtil.serialVersionUID;

    private static SpringContextUtil springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);
    private final static String alg = springContextUtil.getAlg();
    private final static String typ = "JWT";
    private final static String toeknSalt = springContextUtil.getTokenSalt();
    //对设置的加密 密文 进行复杂化 处理 防止设置的长度不足
    private final static String Subject = springContextUtil.getTokeSubject();
    public static final String ClaimName = "TokenPayloadAbs";
    private final static long TOKEN_EXPIRE_MILLIS = 1000 * 60 * springContextUtil.getTokenExpireMinute();


    public final String createToken(T t){
        return createTokenBuilder(t).compact();
    }

    protected abstract JwtBuilder createTokenBuilder(T t);

    public final <T extends TokenPayloadAbs> T decodeToken(Class<T> t, String token){
        return TokenPayloadAbs.getObject(t,decodeTokenBuilder(token));
    }

    protected abstract T decodeTokenBuilder(String token);
}
