package cn.com.filter.token;

import cn.com.SpringContextUtil;
import cn.com.filter.token.Body.TokenPayloadAbs;
import cn.com.filter.token.Key.KeyFactory;
import cn.com.utils.Redis.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;


public class JWTUtils implements KeyFactory {
    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private JedisCluster jedisCluster;

    public static SpringContextUtil springContextUtil = SpringContextUtil.getBean(SpringContextUtil.class);

    private final String alg = springContextUtil.getAlg();
    private final String typ = "JWT";
    public static final String toeknSalt = springContextUtil.getTokenSalt();
    //对设置的加密 密文 进行复杂化 处理 防止设置的长度不足
    private final String Subject = springContextUtil.getTokeSubject();

    public static final String ClaimName = "TokenPayloadAbs";
    private static final long serialVersionUID = SpringContextUtil.serialVersionUID;
    private final long TOKEN_EXPIRE_MILLIS = 1000 * 60 * springContextUtil.getTokenExpireMinute();

    private TokenPayloadAbs t;
    private KeyFactory k;

    private JWTUtils(){}

    public JWTUtils(TokenPayloadAbs body,KeyFactory key){
        this.t = body;
        this.k = key;
    }

    public String builder(){
        long l = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setHeader(getHeader())
                .setId(String.valueOf(t.getUUID()))
                .setSubject(Subject)
                .setIssuedAt(new Date(l))
                .setExpiration(new Date(l + TOKEN_EXPIRE_MILLIS));
        t.inItJwtBuilder(t, builder);
        builder.signWith(getPublicKey(toeknSalt));
        return builder.compact();
    }

    public TokenPayloadAbs getPayload(String token){
        Claims claims = getClaims(token, getPublicKey(toeknSalt));
        String uuid = claims.get("UUID").toString();
        redisUtil.get("admin");
        if (getPrivate(toeknSalt) != null){

        }else {

            try {
                return (TokenPayloadAbs) JSONObject.parseObject(JSONObject.toJSONString(claims),Class.forName((String) claims.get("TypeClass")));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private boolean isExpiration(String token,Key key){
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Claims getClaims(String token,Key key){
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    };

    @Override
    public Key getPublicKey(String key) {
        return k.getPublicKey(key);
    }

    @Override
    public Key getPrivate(String key) {
        return k.getPrivate(key);
    }

    private HashMap<String, Object> getHeader() {
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", alg);
        header.put("typ", typ);
        return header;
    }

}
