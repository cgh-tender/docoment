package cn.com.filter.token.Body.Impl;

import cn.com.filter.token.Body.TokenPayloadAbs;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;

@Data
@Log4j
public class TokenUserPhonePayload extends TokenPayloadAbs {

    public TokenUserPhonePayload() {
        super();
    }

    public TokenUserPhonePayload(String Phone, HttpServletRequest request) {
        super(request);
        this.Phone = Phone;
    }

    public String Phone;

    @Override
    public TokenPayloadAbs getPayload(String v, HttpServletRequest request) {
        return null;
    }

    @Override
    public String toString() {
        return "TokenUserPhonePayload{" +
                "Phone='" + Phone + '\'' +
                ", UUID=" + uuid +
                ", IP='" + ip + '\'' +
                '}';
    }
}
