package cn.com.filter.token;

import cn.com.filter.token.Body.TokenPayloadAbs;
import io.jsonwebtoken.Claims;

public interface TokenVerifyService {
    /**
     * Token是否过期 是: true , 否 false
     */
    Boolean isOverdue();
    /**
     * Token是否过期 是: true , 否 false
     */
    Boolean SysIsOverdue();
    /**
     * 重新加载出一个token
     * @return
     */
    String reToken();

    /**
     * 保存Token
     * @return true 成功
     */
    Boolean saveToken();

    /**
     * 修改存入时间
     * @return true 成功
     */
    Boolean upTokenTime();
    Claims getClaims();
    TokenPayloadAbs decodeToken();
}
