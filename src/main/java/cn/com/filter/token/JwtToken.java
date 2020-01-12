package cn.com.filter.token;

import cn.com.SpringContextUtil;
import com.alibaba.fastjson.JSONObject;
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

    @Override
    public String createJwtToken(TokenPayloadAbs payload) throws Exception {
        return null;
    }

    public static  <T> T getTokenPayloadAbs(String token){
       return null;
    }

    public static String getTokenUUID(String token){
       return null;
    }

    private static HashMap<String,Object> getHeader(){
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg",alg);
        header.put("typ",typ);
        return header;
    }
    private static HashMap<String,Object> getSecretKey(){

        return null;
    }
}

interface JwtSuper{
    String createJwtToken(TokenPayloadAbs payload) throws Exception;
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
