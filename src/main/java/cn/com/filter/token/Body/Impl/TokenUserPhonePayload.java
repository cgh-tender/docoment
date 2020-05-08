package cn.com.filter.token.Body.Impl;

import cn.com.filter.token.Body.TokenPayloadAbs;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Log4j
public class TokenUserPhonePayload extends TokenPayloadAbs implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    private String phone;
    private String passWord;

    public TokenUserPhonePayload() {
        super();
    }

    public TokenUserPhonePayload(String Phone, String password, HttpServletRequest request) {
        super(request);
        this.passWord = password;
        this.phone = Phone;
    }

    @Override
    public String toString() {
        return "TokenUserPhonePayload{" +
                "phone='" + phone + '\'' +
                ", passWord='" + passWord + '\'' +
                ", uuid=" + uuid +
                ", ip='" + ip + '\'' +
                ", role='" + role + '\'' +
                ", loginNum=" + loginNum +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
