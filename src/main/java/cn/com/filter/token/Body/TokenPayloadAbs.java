package cn.com.filter.token.Body;

import cn.com.SpringContextUtil;
import cn.com.utils.algor.SnowFlake;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public abstract class TokenPayloadAbs {

    public Long uuid; // 每个 Token 唯一
    public String ip; // 申请每个 Token 地址

    public TokenPayloadAbs(){}

    /**
     * @param request 请求的 Request
     */
    public abstract TokenPayloadAbs getPayload(String v, HttpServletRequest request);

    public TokenPayloadAbs(HttpServletRequest request){
        final SnowFlake snowFlake = SpringContextUtil.getBean(SnowFlake.class);
        this.uuid = snowFlake.nextId();
        this.ip = request.getRemoteAddr();
    }
}
