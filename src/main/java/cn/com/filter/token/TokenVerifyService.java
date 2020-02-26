package cn.com.filter.token;

import cn.com.filter.token.Body.TokenPayloadAbs;
import io.jsonwebtoken.Claims;

public interface TokenVerifyService {
    /**
     * 是否过期 是: true , 否 false
     */
    Boolean isOverdue();

    /**
     * 重新加载出一个token
     * @return
     */
    String reToken();
    Claims getClaims();
    TokenPayloadAbs decodeToken();
}
