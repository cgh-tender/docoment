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
public class TokenUserPhonePayload extends TokenPayloadAbs {

    private TokenUserPhonePayload() {
        super();
    }

    public TokenUserPhonePayload(String Phone, HttpServletRequest request){
        super(request, JwtType.PHONE);
        final SnowFlake snowFlake = SpringContextUtil.getBean(SnowFlake.class);
        this.UUID = snowFlake.nextId();
        this.Phone = Phone;
    }

    public String Phone;

    @Override
    public String toString() {
        return "TokenUserPhonePayload{" +
                "Phone='" + Phone + '\'' +
                "TokenPayloadAbs='" + super.toString()+ '\'' +
                '}';
    }
}
