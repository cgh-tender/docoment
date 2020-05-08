package cn.com.filter.token.Body.Impl;

import cn.com.filter.token.Body.TokenPayloadAbs;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Log4j
public class TokenUserNamePayload extends TokenPayloadAbs implements Serializable {
    private static final long serialVersionUID = 5516075349620653480L;
    private String userName;
    private String passWord;

    public TokenUserNamePayload() {
        super();
    }

    public TokenUserNamePayload(String userName, String password, HttpServletRequest request) {
        super(request);
        this.passWord = password;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TokenUserNamePayload{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", uuid=" + uuid +
                ", ip='" + ip + '\'' +
                ", role='" + role + '\'' +
                ", loginNum=" + loginNum +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}

