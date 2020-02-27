package cn.com.filter.token.Body.Impl;

import cn.com.filter.token.Body.TokenPayloadAbs;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;

@Data
@Log4j
public class TokenUserPhonePayload extends TokenPayloadAbs {
    private String phone;
    private String passWord;
    public TokenUserPhonePayload() {
        super();
    }

    public TokenUserPhonePayload(String Phone, String password,HttpServletRequest request) {
        super(request);
        this.passWord = password;
        this.phone = Phone;
    }

    @Override
    public String toString() {
        return "TokenUserPhonePayload{" +
                "phone='" + phone + '\'' +
                ", password='" + passWord + '\'' +
                ", uuid=" + uuid +
                ", ip='" + ip + '\'' +
                '}';
    }
}
