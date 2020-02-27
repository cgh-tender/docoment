package cn.com.filter.token.Body.Impl;

import cn.com.filter.token.Body.TokenPayloadAbs;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;

@Data
@Log4j
public class TokenUserNamePayload extends TokenPayloadAbs {
    private String userName;
    private String passWord;

    public TokenUserNamePayload() {
        super();
    }

    public TokenUserNamePayload(String userName, String password,HttpServletRequest request) {
        super(request);
        this.passWord = password;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TokenUserNamePayload{" +
                "userName='" + userName + '\'' +
                ", password='" + passWord + '\'' +
                ", uuid=" + uuid +
                ", ip='" + ip + '\'' +
                '}';
    }

}

