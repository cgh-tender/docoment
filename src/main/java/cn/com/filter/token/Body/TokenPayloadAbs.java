package cn.com.filter.token.Body;

import cn.com.SpringContextUtil;
import cn.com.filter.token.Utils.TokenSnowFlake;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public abstract class TokenPayloadAbs extends UsernamePasswordToken implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    // 每个 Token 唯一
    public Long uuid;
    // 每个 Token 登录地址
    public String ip;
    // 每个 Token 用户权限
    public String role;
    // 每个 Token 登录次数
    public int loginNum;

    public TokenPayloadAbs() {
        super();
    }

    public TokenPayloadAbs(HttpServletRequest request){
        final TokenSnowFlake snowFlake = SpringContextUtil.getBean(TokenSnowFlake.class);
        this.uuid = snowFlake.nextId();
        this.ip = request.getRemoteAddr();
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(int loginNum) {
        this.loginNum = loginNum;
    }
}
