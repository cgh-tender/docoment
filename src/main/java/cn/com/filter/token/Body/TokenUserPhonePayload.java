package cn.com.filter.token.Body;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.JwtBuilder;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Data
@Log4j
public class TokenUserPhonePayload extends TokenPayloadAbs {

    public TokenUserPhonePayload(String Phone, HttpServletRequest request) {
        super(TokenUserPhonePayload.class, request);
        this.Phone = Phone;
    }

    public String Phone;

    @Override
    public TokenPayloadAbs getPayload(String v, HttpServletRequest request) {
        return null;
    }

    @Override
    public JwtBuilder inItJwtBuilder(TokenPayloadAbs t, JwtBuilder builder) {
        String t1 = JSONObject.toJSONString(t);
        builder.setClaims(JSONObject.parseObject(t1, Map.class));
        return builder;
    }

    @Override
    public String toString() {
        return "TokenUserPhonePayload{" +
                "Phone='" + Phone + '\'' +
                ", UUID=" + UUID +
                ", IP='" + IP + '\'' +
                ", TypeClass=" + TypeClass +
                '}';
    }
}
