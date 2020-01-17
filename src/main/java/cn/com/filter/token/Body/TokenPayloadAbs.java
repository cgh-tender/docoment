package cn.com.filter.token.Body;

import cn.com.SpringContextUtil;
import cn.com.utils.algor.SnowFlake;
import io.jsonwebtoken.JwtBuilder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Data
public abstract class TokenPayloadAbs implements Serializable {
    public Long serialVersionUID = SpringContextUtil.serialVersionUID;

    public Long UUID; // 每个 Token 唯一
    public String IP; // 申请每个 Token 地址
    public Class TypeClass;

    private TokenPayloadAbs(){}

    /**
     * @param request 请求的 Request
     */
    public abstract TokenPayloadAbs getPayload(String v, HttpServletRequest request);

    public TokenPayloadAbs(Class r, HttpServletRequest request){
        final SnowFlake snowFlake = SpringContextUtil.getBean(SnowFlake.class);
        this.UUID = snowFlake.nextId();
        this.TypeClass = r;
        this.IP = request.getRemoteAddr();
    }


    public abstract JwtBuilder inItJwtBuilder(TokenPayloadAbs t, JwtBuilder builder);

}
