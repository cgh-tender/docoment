package cn.com.filter.token;

import cn.com.SpringContextUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Data
@Log4j
public abstract class TokenPayloadAbs implements Serializable {
    public Long serialVersionUID = SpringContextUtil.serialVersionUID;

    public Long UUID; // 每个 Token 唯一
    public String IP; // 申请每个 Token 地址
    public JwtType type; // 申请的类型
    public String alg;//Token 加密方式

    public TokenPayloadAbs(){}
    /**
     * @param request 请求的 Request
     * @param type 用户注册类型
     */
    public TokenPayloadAbs(HttpServletRequest request,JwtType type){
        this.type = type;
        this.IP = request.getRemoteAddr();
    }

    public static <R> R getObject(Class<R> r,TokenPayloadAbs abs){
        return (R) abs;
    }

}
