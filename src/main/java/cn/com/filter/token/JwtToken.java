package cn.com.filter.token;

import cn.com.SpringContextUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Log4j
public class JwtToken implements Serializable,JwtSuper {

    private final static String alg = "SHA256"; //HMAC SHA256
    private final static String typ = "JWT";
    private final static String ClaimName = "TokenPayloadAbs";
    private static final Long serialVersionUID = SpringContextUtil.serialVersionUID;

    public String createUserNameJwtToken(TokenUserNamePayload payload) throws Exception {
        return createJwtToken(payload,getAlgorithm(payload.getUserName()));
    }

    public String createUserPhoneJwtToken(TokenUserPhonePayload payload) throws Exception {
        return createJwtToken(payload,getAlgorithm(payload.getPhone()));
    }

    @Override
    public String createJwtToken(TokenPayloadAbs payload,Algorithm secret) throws Exception {
        JWTCreator.Builder builder = JWT.create();
        HashMap<String, Object> header = getHeader();
        Long uuid = payload.getUUID();
        builder.withAudience(JSONObject.toJSONString(payload));
        builder.withClaim(ClaimName,JSONObject.toJSONString(payload));
        builder.withHeader(header);
        builder.withJWTId(String.valueOf(uuid));
        Date date = new Date();
        builder.withIssuedAt(date);
        builder.withNotBefore(date);
        builder.withExpiresAt(new Date(date.getTime() + 10000));
        return builder.sign(secret);
    }

    /**
     * 获取 getAlgorithm 加密 code 是当前唯一值
     * @param code 唯一值
     * @return Algorithm
     * @throws Exception 返回错误类型写回前台
     */
    private static Algorithm getAlgorithm(String code) throws Exception {
        final String error = "当前Token不支持当前的加密方式[%s]请联系管理员";
        if (StringUtils.equals(alg,"SHA256")){
            return Algorithm.HMAC256(SpringContextUtil.TOKENSALT.getBytes());
        }else if (StringUtils.equals(alg,"HMAC")){
            return Algorithm.HMAC256(SpringContextUtil.TOKENSALT.getBytes());
        }else {
            throw new Exception(String.format(error,alg));
        }
    }

    public static  <T> T getTokenPayloadAbs(String token){
        DecodedJWT decode = JWT.decode(token);
        String header = decode.getHeader();
        Claim claim = decode.getClaim(ClaimName);
        List<String> audience = decode.getAudience();
        log.info(audience);
        return null;
    }

    public static String getTokenUUID(String token){
        return JWT.decode(token).getId();
    }

    private static HashMap<String,Object> getHeader(){
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg",alg);
        header.put("typ",typ);
        return header;
    }
}

interface JwtSuper{
    String createJwtToken(TokenPayloadAbs payload,Algorithm secret) throws Exception;
}

enum JwtType{
    USERNAME("userName"),PHONE("userPhone");
    public String type;

    JwtType(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
