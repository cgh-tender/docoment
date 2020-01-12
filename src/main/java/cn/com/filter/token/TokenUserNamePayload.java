package cn.com.filter.token;

import cn.com.SpringContextUtil;
import cn.com.utils.algor.SnowFlake;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;

@EqualsAndHashCode(callSuper = true)
@Data
@Log4j
public class TokenUserNamePayload extends TokenPayloadAbs {

    private TokenUserNamePayload(){
        super();
    }

    public TokenUserNamePayload(String userName, HttpServletRequest request){
        super(request,JwtType.USERNAME);
        final SnowFlake snowFlake = SpringContextUtil.getBean(SnowFlake.class);
        this.UUID = snowFlake.nextId();
        this.userName = userName;
    }

    private String userName;

    @Override
    public String toString() {
        return "TokenUserPhonePayload{" +
                "userName='" + userName + '\'' +
                "TokenPayloadAbs='" + super.toString()+ '\'' +
                '}';
    }
}

