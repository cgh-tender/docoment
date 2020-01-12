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

    public Long UUID;
    public String IP;
    public JwtType type;

    public TokenPayloadAbs(){}
    /**
     * @param request 请求的 Request
     * @param type 用户注册类型
     */
    public TokenPayloadAbs(HttpServletRequest request,JwtType type){
        this.type = type;
        this.IP = request.getRemoteAddr();
    }

    public static  <T extends TokenPayloadAbs> T getObject(T obj){
        return obj;
    }

    @Override
    public String toString() {
        return "TokenPayloadAbs{" +
                "UUID=" + UUID +
                ", IP='" + IP + '\'' +
                ", type=" + type +
                '}';
    }
}
