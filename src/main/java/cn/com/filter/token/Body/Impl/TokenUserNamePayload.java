package cn.com.filter.token.Body.Impl;

import cn.com.filter.token.Body.TokenPayloadAbs;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;

@Data
@Log4j
public class TokenUserNamePayload extends TokenPayloadAbs {

    public TokenUserNamePayload() {
        super();
    }

    public TokenUserNamePayload(String userName, HttpServletRequest request) {
        super(request);
        this.userName = userName;
    }

    private String userName;

    @Override
    public TokenPayloadAbs getPayload(String userName, HttpServletRequest request) {
        this.userName = userName;
        return this;
    }

    @Override
    public String toString() {
        return "TokenUserNamePayload{" +
                "userName='" + userName + '\'' +
                ", UUID=" + uuid +
                ", IP='" + ip + '\'' +
                '}';
    }
}

