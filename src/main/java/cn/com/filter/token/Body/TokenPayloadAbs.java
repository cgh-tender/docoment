package cn.com.filter.token.Body;

import cn.com.SpringContextUtil;
import cn.com.utils.algor.SnowFlake;
import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.http.HttpServletRequest;

@Data
public abstract class TokenPayloadAbs extends UsernamePasswordToken {

    public Long uuid; // 每个 Token 唯一
    public String ip;
    public TokenPayloadAbs(){}

    public TokenPayloadAbs(HttpServletRequest request){
        final SnowFlake snowFlake = SpringContextUtil.getBean(SnowFlake.class);
        this.uuid = snowFlake.nextId();
        this.ip = request.getRemoteAddr();
    }

}
