package cn.com.filter.token.Body;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.JwtBuilder;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Data
@Log4j
public class TokenUserNamePayload extends TokenPayloadAbs {

    public TokenUserNamePayload(String userName, HttpServletRequest request) {
        super(TokenUserNamePayload.class, request);
        this.userName = userName;
    }

    private String userName;

    @Override
    public TokenPayloadAbs getPayload(String userName, HttpServletRequest request) {
        this.userName = userName;
        return this;
    }

    @Override
    public JwtBuilder inItJwtBuilder(TokenPayloadAbs t, JwtBuilder builder) {
        String t1 = JSONObject.toJSONString(t);
        builder.setClaims(JSONObject.parseObject(t1, Map.class));
        return builder;
    }

    @Override
    public String toString() {
        return "TokenUserNamePayload{" +
                "userName='" + userName + '\'' +
                ", UUID=" + UUID +
                ", IP='" + IP + '\'' +
                ", TypeClass=" + TypeClass +
                '}';
    }
}

